package de.cosmocode.palava.tracking;

import java.net.InetAddress;
import java.net.URI;

/**
 * 
 *
 * @author Willi Schoenborn
 */
public interface HttpSession {

   URI getRequestUri();
   
   InetAddress getRemoteAddress();
   
   String getUserAgent();
   
   String getSessionId();
    
}
