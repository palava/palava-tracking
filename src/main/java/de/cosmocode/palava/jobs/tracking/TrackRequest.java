package de.cosmocode.palava.jobs.tracking;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.cosmocode.palava.core.call.Call;
import de.cosmocode.palava.core.command.Command;
import de.cosmocode.palava.core.command.CommandException;
import de.cosmocode.palava.core.protocol.content.Content;
import de.cosmocode.palava.core.protocol.content.JsonContent;
import de.cosmocode.palava.core.request.HttpRequest;
import de.cosmocode.palava.services.tracking.TrackingService;

/**
 * Tracks the current {@link HttpRequest} using the default
 * binding for {@link TrackingService}.
 *
 * @author Willi Schoenborn
 */
@Singleton
public class TrackRequest implements Command {

    @Inject
    private TrackingService service;
    
    @Override
    public Content execute(Call call) throws CommandException {
        service.save(call.getHttpRequest());
        return JsonContent.EMPTY;
    }

}
