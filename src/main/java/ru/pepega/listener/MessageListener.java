package ru.pepega.listener;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class MessageListener {

    public Mono<Void> processCommands(Message eventMessage) {

        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!todo"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Я умею подрубать музыку!\n" +
                        " !join что бы присоедениться к голосовому каналу\n" +
                        "Список команд плеера: \n" +
                        " !play :male_sign: \n" +
                        " !stop :male_sign: \n" +
                        " !pause :male_sign: \n"))
                .then();

    }
}
