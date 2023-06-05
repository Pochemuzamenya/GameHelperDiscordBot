package org.filatov.service.command.appcommand;

import discord4j.discordjson.json.ApplicationCommandRequest;
import discord4j.rest.RestClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class AppCommandRegistrar {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Value("${guild_id}")
    private String guildId;

    protected void register(ApplicationCommandRequest request, RestClient client) {
        assert client != null : "Фейл с регистрацией в " + this.getClass().getName();
        LOG.info("Registrar online");
        Long appId = client.getApplicationId().block();
        long guildId = Long.parseLong(this.guildId);
        LOG.info("App id: {}, guild id: {}, request name: {}", appId, guildId, request.name());

        client.getApplicationService()
                .createGuildApplicationCommand(appId, guildId, request)
                .subscribe();
    }
}
