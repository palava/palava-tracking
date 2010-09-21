package de.cosmocode.palava.tracking;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import com.google.common.base.Preconditions;

import de.cosmocode.palava.ipc.Browser;

/**
 * 
 *
 * @since 
 * @author Willi Schoenborn
 */
final class SimpleConnectionInformation implements ConnectionInformation {

    private final Browser browser;
    private final Map<Serializable, Serializable> metaInformation;
    
    public SimpleConnectionInformation(Browser browser, MetaInformationProvider provider) {
        this.browser = Preconditions.checkNotNull(browser, "Browser");
        if (provider == null) {
            this.metaInformation = Collections.emptyMap();
        } else {
            this.metaInformation = Preconditions.checkNotNull(provider, "MetaInformationProvider").get();
        }
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
