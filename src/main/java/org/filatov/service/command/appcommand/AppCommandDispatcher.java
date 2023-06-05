package org.filatov.service.command.appcommand;

import discord4j.discordjson.json.ApplicationCommandRequest;
import discord4j.rest.RestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppCommandDispatcher {

    private final AppCommandRegistrar registrar;

    private final AppCommandRemover remover;

    private final AppCommandUpdater updater;

    public void register(ApplicationCommandRequest request, RestClient client) {
        registrar.register(request, client);
    }

    public void remove() {
        remover.remove();
    }

    public void update() {
        updater.update();
    }
}
