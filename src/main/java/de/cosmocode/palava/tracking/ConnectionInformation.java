package de.cosmocode.palava.tracking;

import java.io.Serializable;
import java.util.Map;

import de.cosmocode.palava.ipc.Browser;

public interface ConnectionInformation {

    Browser getBrowser();
    
    Map<Serializable, Serializable> getMetaInformation();
    
}
