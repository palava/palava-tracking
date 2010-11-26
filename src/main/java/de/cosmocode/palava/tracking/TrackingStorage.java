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


/**
 * Has to be implemented to store the tracked requests.
 *
 * @author Willi Schönborn
 * @author Tobias Sarnowski
 */
public interface TrackingStorage {

    /**
     * Will provide a list of tracked requests. (Not necessarily the count of the configured
     * batchsize)
     *
     * Note: This call will not necessarily run asynchronous. If the storage requires some time, consider to go into
     * the background yourself.
     *
     * @param requests all incoming request
     */
    void store(Iterable<? extends ConnectionInformation> requests);
    
}
