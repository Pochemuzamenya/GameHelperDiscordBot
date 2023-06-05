package org.filatov.appcommand;

import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;
import org.springframework.stereotype.Component;

@Component
public class PopularItemCommand implements AppCommand {

    @Override
    public ApplicationCommandRequest command() {
        return ApplicationCommandRequest.builder()
                .name("items")
                .description("Популярные сборки героев с opendota")
                .addOption(ApplicationCommandOptionData.builder()
                        .name("heroname")
                        .description("Имя героя для которого хотим получить сборку")
                        .type(ApplicationCommandOption.Type.STRING.getValue())
                        .required(true)
                        .build())
                .build();
    }
}
