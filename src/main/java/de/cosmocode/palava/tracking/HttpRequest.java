/**
 * palava - a java-php-bridge
 * Copyright (C) 2007-2010  CosmoCode GmbH
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

package de.cosmocode.palava.tracking;

import java.net.URI;

/**
 * Simple value object used by {@link TrackingService}s to handle requests.
 *
 * @author Willi Schoenborn
 */
public interface HttpRequest {

    /**
     * Provides the requested uri.
     * 
     * @return the uri
     */
    URI getRequestUri();
   
    /**
     * Provides the referer.
     * 
     * @return the referer or null if there is none
     */
    URI getReferer();
   
    /**
     * Provides the remote address of the client.
     * 
     * @return the remove address
     */
    String getRemoteAddress();
   
    /**
     * Provides the user agent of the client.
     * 
     * @return the user agent or null if there is none
     */
    String getUserAgent();
   
    /**
     * Provides the session id of the request's session.
     * 
     * @return the session id
     */
    String getSessionId();
    
}
