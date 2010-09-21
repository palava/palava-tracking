package de.cosmocode.palava.tracking;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

import de.cosmocode.palava.core.Registry;
import de.cosmocode.palava.core.lifecycle.Disposable;
import de.cosmocode.palava.core.lifecycle.Initializable;
import de.cosmocode.palava.core.lifecycle.LifecycleException;
import de.cosmocode.palava.ipc.Browser;
import de.cosmocode.palava.ipc.Current;
import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCallCreateEvent;
import de.cosmocode.palava.ipc.IpcConnection;
import de.cosmocode.palava.ipc.IpcConnectionDestroyEvent;

/**
 * A service which listens to specific events and collects {@link ConnectionInformation}s.
 *
 * @since 1.0
 * @author Willi Schoenborn
 */
final class DefaultTrackingService implements 
        IpcCallCreateEvent, IpcConnectionDestroyEvent, Initializable, Disposable {

    private static final String CURRENT_BROWSER = DefaultTrackingService.class.getName() + ".CURRENT_BROWSER";
    
    private final Registry registry;
    private final Provider<Browser> currentBrowser;
    private final TrackingStorage storage;
    
    private int batchSize = 50;
    private final Queue<ConnectionInformation> queue = new ConcurrentLinkedQueue<ConnectionInformation>();

    private MetaInformationProvider informationProvider;
    
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
    
    @Inject(optional = true)
    void setInformationProvider(MetaInformationProvider informationProvider) {
        this.informationProvider = Preconditions.checkNotNull(informationProvider, "InformationProvider");
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
        }
    }
    
    @Override
    public void eventIpcConnectionDestroy(IpcConnection connection) {
        final Browser browser = connection.get(CURRENT_BROWSER);
        if (browser == null) {
            return;
        } else {
            queue.offer(new SimpleConnectionInformation(browser, informationProvider));
            
            if (queue.size() >= batchSize) {
                final List<ConnectionInformation> drained = Lists.newArrayListWithCapacity(batchSize);

                for (int i = 0; i < batchSize; i++) {
                    final ConnectionInformation head = queue.poll();
                    Preconditions.checkNotNull(head, "Head");
                    drained.add(head);
                }
                
                storage.store(drained);
            }
        }
    }
    
    @Override
    public void dispose() throws LifecycleException {
        registry.remove(this);
    }
    
}
