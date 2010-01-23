/**
 * palava - a java-php-bridge
 * Copyright (C) 2007  CosmoCode GmbH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
