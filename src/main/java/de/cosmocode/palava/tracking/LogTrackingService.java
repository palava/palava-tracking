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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Singleton;

/**
 * Default implementation of the {@link TrackingService} which
 * logs all incoming requests using its {@link Logger}.
 *
 * @author Willi Schoenborn
 */
@Singleton
final class LogTrackingService implements TrackingService {

    private static final Logger LOG = LoggerFactory.getLogger(LogTrackingService.class);

    @Override
    public void save(HttpRequest request) {
        Preconditions.checkNotNull(request, "Request");
        LOG.info("Request on {} (address={}, sessionId={}, userAgent={})", new Object[] {
            request.getRequestUri(), request.getRemoteAddress(), 
            request.getSessionId(), request.getUserAgent()
        });
    }

}
