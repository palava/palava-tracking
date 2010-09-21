package de.cosmocode.palava.tracking;


public interface TrackingStorage {

    void store(Iterable<? extends ConnectionInformation> requests);
    
}
