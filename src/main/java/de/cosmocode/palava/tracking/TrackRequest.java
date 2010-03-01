/**
 * palava - a java-php-bridge
 * Copyright (C) 2007-2010  CosmoCode GmbH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
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
import de.cosmocode.palava.ipc.IpcCommandExecutionException;
import de.cosmocode.palava.ipc.IpcCommand.Description;
import de.cosmocode.palava.ipc.IpcCommand.Param;
import de.cosmocode.palava.ipc.IpcCommand.Params;
import de.cosmocode.palava.ipc.IpcCommand.Throw;

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
