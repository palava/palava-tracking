package de.cosmocode.palava.services.tracking;

import de.cosmocode.palava.core.inject.AbstractApplicationModule;

/**
 * Binds the {@link LogTrackingService} as default implementation
 * for {@link TrackingService} and activates the {@link TrackingFilter} for
 * every request.
 *
 * @author Willi Schoenborn
 */
public final class TrackingModule extends AbstractApplicationModule {

    @Override
    protected void configureApplication() {
        serve(TrackingService.class).with(LogTrackingService.class);
        filterRequestsWith(TrackingFilter.class);
    }

}
