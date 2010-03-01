package de.cosmocode.palava.tracking;

import java.net.URI;

/**
 * 
 *
 * @author Willi Schoenborn
 */
public interface HttpRequest {

   URI getRequestUri();
   
   URI getReferer();
   
   String getRemoteAddress();
   
   String getUserAgent();
   
   String getSessionId();
    
}
