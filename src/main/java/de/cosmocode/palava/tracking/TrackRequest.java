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
import java.net.URISyntaxException;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.cosmocode.palava.ipc.IpcArguments;
import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCommand;
import de.cosmocode.palava.ipc.IpcCommand.Description;
import de.cosmocode.palava.ipc.IpcCommand.Param;
import de.cosmocode.palava.ipc.IpcCommand.Params;
import de.cosmocode.palava.ipc.IpcCommand.Throw;
import de.cosmocode.palava.ipc.IpcCommandExecutionException;

/**
 * See below.
 *
 * @author Willi Schoenborn
 */
@Description("Tracks an http request using the currently bound TrackingService.")
@Params({
    @Param(name = TrackRequest.REQUEST_URI, type = "uri", description = "The requested uri"),
    @Param(
        name = TrackRequest.REFERER, 
        type = "uri", 
        description = "The referer (last visited page)", 
        optional = true,
        defaultValue = "null"
    ),
    @Param(name = TrackRequest.REMOTE_ADDRESS, type = "ip address", description = "The user's remote address"),
    @Param(
        name = TrackRequest.USER_AGENT, 
        type = "String", 
        description = "The browser's user agent",
        optional = true,
        defaultValue = "null"
    )
})
@Throw(name = URISyntaxException.class, description = "If the given string violates RFC 2396")
@Singleton
public class TrackRequest implements IpcCommand {

    public static final String REQUEST_URI = "requestUri";
    public static final String REFERER = "referer";
    public static final String REMOTE_ADDRESS = "remoteAddress";
    public static final String USER_AGENT = "userAgent";
    
    private final TrackingService service;

    @Inject
    public TrackRequest(TrackingService service) {
        this.service = Preconditions.checkNotNull(service, "Service");
    }
    
    @Override
    public void execute(final IpcCall call, Map<String, Object> result) throws IpcCommandExecutionException {
        final IpcArguments arguments = call.getArguments();
        
        final URI requestUri;
        final URI referer;
        
        try {
            requestUri = new URI(arguments.getString(REQUEST_URI));
            final String refererValue = arguments.getString(REFERER, null);
            referer = refererValue == null ? null : new URI(arguments.getString(REFERER));
        } catch (URISyntaxException e) {
            throw new IpcCommandExecutionException(e);
        }
        
        final String remoteAddress = arguments.getString(REMOTE_ADDRESS);
        final String userAgent = arguments.getString(USER_AGENT, null);
        
        service.save(new HttpRequest() {
            
            @Override
            public URI getRequestUri() {
                return requestUri;
            }
            
            @Override
            public URI getReferer() {
                return referer;
            }
            
            @Override
            public String getRemoteAddress() {
                return remoteAddress;
            }
            
            @Override
            public String getUserAgent() {
                return userAgent;
            }
            
            @Override
            public String getSessionId() {
                return call.getConnection().getSession().getSessionId();
            }
            
        });
    }

}
