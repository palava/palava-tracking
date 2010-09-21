package de.cosmocode.palava.tracking;

import java.io.Serializable;
import java.util.Map;

public interface MetaInformationProvider {

    Map<Serializable, Serializable> get();
    
}
