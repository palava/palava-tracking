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
