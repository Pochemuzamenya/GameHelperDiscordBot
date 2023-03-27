package org.filatov.listener;

import discord4j.core.object.entity.Message;
import lombok.RequiredArgsConstructor;
import org.filatov.repo.MemberRepository;
import org.filatov.service.command.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.Map;

@RequiredArgsConstructor
public abstract class MessageListener {
//    @Autowired
//    private Map<String, Mono<String>> commands;
//    @Autowired
//    private MemberRepository repo;
//
    private final CommandExecutor executor;

    public Mono<Void> processCommands(Message eventMessage) {

        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(Message::getChannel)
                //.flatMap(channel -> channel.createMessage(commands.get(eventMessage.getContent()).block()))
                .flatMap(channel -> channel.createMessage(executor.execute(eventMessage).block()))
                .then();
    }
}
