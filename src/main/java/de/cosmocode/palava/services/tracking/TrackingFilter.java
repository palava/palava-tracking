package de.cosmocode.palava.services.tracking;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.cosmocode.palava.core.request.HttpRequest;
import de.cosmocode.palava.core.request.RequestFilter;

/**
 * Intercepts certain requests and delegates to the
 * default binding for {@link TrackingService}.
 *
 * @author Willi Schoenborn
 */
@Singleton
public final class TrackingFilter implements RequestFilter {

    private final TrackingService service;
    
    @Inject
    public TrackingFilter(TrackingService service) {
        this.service = Preconditions.checkNotNull(service, "Service");
    }
    
    @Override
    public void before(HttpRequest request) {
        service.save(request);
    }

    @Override
    public void after(HttpRequest request) {
        
    }

}
