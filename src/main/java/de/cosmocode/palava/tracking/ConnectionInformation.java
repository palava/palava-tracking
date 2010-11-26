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

import java.io.Serializable;
import java.util.Map;

import de.cosmocode.palava.ipc.Browser;

/**
 * Represents one tracked request.
 *
 * @author Willi Schönborn
 * @author Tobias Sarnowski
 */
public interface ConnectionInformation {

    /**
     * When the request was tracked.
     *
     * @return the current time in milliseconds
     */
    long getCurrentTimeMillis();

    /**
     * The {@link Browser} informations.
     *
     * @return the current browser
     */
    Browser getBrowser();

    /**
     * Additional assorted informations about the request provided by {@link ConnectionInformationProvider}s.
     *
     * @return associated meta information 
     */
    Map<Serializable, Serializable> getMetaInformation();
    
}
