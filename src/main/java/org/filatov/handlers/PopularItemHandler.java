package org.filatov.handlers;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.channel.GuildMessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
public class PopularItemHandler implements CommandHandler{

    @Override
    public Mono<Void> handleCommand(ChatInputInteractionEvent event) {
        EmbedCreateSpec test = EmbedCreateSpec.builder()
                .color(Color.BISMARK)
                .title("Test")
                .image("https://dota2.ru/img/items/blade_mail.jpg?1682088704")
                .addField("Title", "value", false)
                .addField("item", "https://dota2.ru/img/items/aeon_disk.jpg?1671391709", false)
                .image("https://dota2.ru/img/items/aeon_disk.jpg?1671391709")
                .timestamp(Instant.now())
                .build();

        return event.getClient()
                .getChannelById(Snowflake.of(840438500859707445L))
                .ofType(GuildMessageChannel.class)
                .flatMap(channel -> channel.createMessage(test))
                .then();
    }

    @Override
    public String getMyCommandName() {
        return "items";
    }
}
