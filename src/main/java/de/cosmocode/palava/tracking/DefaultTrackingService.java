/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.palava.tracking;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import de.cosmocode.collections.Procedure;
import de.cosmocode.palava.core.Registry;
import de.cosmocode.palava.core.lifecycle.Disposable;
import de.cosmocode.palava.core.lifecycle.Initializable;
import de.cosmocode.palava.core.lifecycle.LifecycleException;
import de.cosmocode.palava.ipc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A service which listens to specific events and collects {@link ConnectionInformation}s.
 *
 * @since 1.0
 * @author Willi Schoenborn
 * @author Tobias Sarnowski
 */
final class DefaultTrackingService implements
    IpcCallCreateEvent, IpcConnectionDestroyEvent, Initializable, Disposable {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultTrackingService.class);

    private static final String CURRENT_BROWSER = DefaultTrackingService.class.getName() + ".CURRENT_BROWSER";
    private static final String REQUEST_TIME = DefaultTrackingService.class.getName() + ".REQUEST_TIME";

    private final Registry registry;
    private final Provider<Browser> currentBrowser;
    private final TrackingStorage storage;

    private int batchSize = 50;
    private final Queue<ConnectionInformation> queue = new ConcurrentLinkedQueue<ConnectionInformation>();

    @Inject
    public DefaultTrackingService(@Nonnull Registry registry, @Current Provider<Browser> currentBrowser,
                                  TrackingStorage storage) {

        this.registry = Preconditions.checkNotNull(registry, "Registry");
        this.currentBrowser = Preconditions.checkNotNull(currentBrowser, "CurrentBrowser");
        this.storage = Preconditions.checkNotNull(storage, "Storage");
    }

    @Inject(optional = true)
    void setBatchSize(@Named("tracking.batchSize") int batchSize) {
        this.batchSize = batchSize;
    }

    @Override
    public void initialize() throws LifecycleException {
        registry.register(IpcCallCreateEvent.class, this);
        registry.register(IpcConnectionDestroyEvent.class, this);
    }

    @Override
    public void eventIpcCallCreate(IpcCall call) {
        final IpcConnection connection = call.getConnection();
        if (connection.contains(CURRENT_BROWSER)) {
            return;
        } else {
            final Browser browser = currentBrowser.get();
            connection.set(CURRENT_BROWSER, browser);
            connection.set(REQUEST_TIME, System.currentTimeMillis());
        }
    }

    @Override
    public void eventIpcConnectionDestroy(IpcConnection connection) {
        final Browser browser = connection.get(CURRENT_BROWSER);
        final Long requestTime = connection.get(REQUEST_TIME);
        if (browser == null) {
            LOG.debug("No browser informations found; probably no call was made within the connection.");
            return;
        } else {
            // collect additional informations
            final Map<Serializable, Serializable> data = Maps.newHashMap();
            registry.notify(ConnectionInformationProvider.class, new Procedure<ConnectionInformationProvider>(){
                @Override
                public void apply(ConnectionInformationProvider provider) {
                    provider.storeInformations(data);
                }
            });

            // save it
            queue.offer(new SimpleConnectionInformation(requestTime, browser, data));

            // trigger the storage engine?
            if (queue.size() >= batchSize) {
                LOG.debug("Batchsize reached; triggering storage engine...");
                storeQueue();
            }
        }
    }

    @Override
    public void dispose() throws LifecycleException {
        LOG.debug("Storing remaining tracked requests...");
        storeQueue();
        registry.remove(this);
    }

    private void storeQueue() {
        final List<ConnectionInformation> drained = Lists.newArrayListWithCapacity(batchSize);

        for (int i = 0; i < batchSize; i++) {
            final ConnectionInformation head = queue.poll();
            Preconditions.checkNotNull(head, "Head");
            drained.add(head);
        }

        storage.store(drained);
    }
}
