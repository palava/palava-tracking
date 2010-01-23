package de.cosmocode.palava.services.tracking;

import de.cosmocode.palava.core.request.HttpRequest;
import de.cosmocode.palava.core.service.Service;

/**
 * A {@link Service} which handles {@link HttpRequest} tracking.
 *
 * @author Willi Schoenborn
 */
public interface TrackingService extends Service {

    /**
     * Tracks the specified request.
     * 
     * @param request the incoming request
     * @throws NullPointerException if request is null
     */
    void save(HttpRequest request);
    
}
