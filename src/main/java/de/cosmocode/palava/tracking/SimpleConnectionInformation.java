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

import com.google.common.base.Preconditions;
import de.cosmocode.palava.ipc.Browser;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 *
 * @since 1.0
 * @author Willi Schoenborn
 * @author Tobias Sarnowski
 */
final class SimpleConnectionInformation implements ConnectionInformation {

    private final Browser browser;
    private final Map<Serializable, Serializable> metaInformation;
    private final long currentTimeMillis;

    /**
     *
     * @param currentTimeMillis System.currentTimeMillis()
     * @param browser
     * @param metaInformation
     */
    public SimpleConnectionInformation(
        long currentTimeMillis,
        Browser browser,
        Map<Serializable,Serializable> metaInformation) {

        this.currentTimeMillis = currentTimeMillis;
        this.browser = Preconditions.checkNotNull(browser, "Browser");
        this.metaInformation = Preconditions.checkNotNull(metaInformation, "MetaInformation");
    }

    @Override
    public long getCurrentTimeMillis() {
        return currentTimeMillis;
    }

    @Override
    public Browser getBrowser() {
        return browser;
    }

    @Override
    public Map<Serializable, Serializable> getMetaInformation() {
        return metaInformation;
    }

}
