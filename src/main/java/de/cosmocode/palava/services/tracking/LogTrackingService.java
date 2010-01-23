package de.cosmocode.palava.services.tracking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Singleton;

import de.cosmocode.palava.core.request.HttpRequest;

/**
 * Default implementation of the {@link TrackingService} which
 * logs all incoming requests using its {@link Logger}.
 *
 * @author Willi Schoenborn
 */
@Singleton
public final class LogTrackingService implements TrackingService {

    private static final Logger log = LoggerFactory.getLogger(LogTrackingService.class);

    @Override
    public void save(HttpRequest request) {
        Preconditions.checkNotNull(request, "Request");
        log.info("Request on {} (address={}, sessionId={}, userAgent={})", new Object[] {
            request.getRequestUri(), request.getRemoteAddress(), 
            request.getHttpSession().getSessionId(), request.getUserAgent()
        });
    }

}
