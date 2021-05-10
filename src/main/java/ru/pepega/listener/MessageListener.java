package ru.pepega.listener;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class MessageListener {

    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("Бот что ты умеешь"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Я умею подрубать музыку!\n" +
                        " что бы навалить музыку с ютаба зайди на голосовой канал и напиши !join\n" +
                        " - когда я залечу за тобой напиши !play и добавь ссылку на видео с ютуб\n"))
                .then();
    }
}
