package org.filatov.service.command;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import lombok.RequiredArgsConstructor;
import org.filatov.model.Member;
import org.filatov.repo.MemberRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final MemberRepository repository;

    public Mono<Member> register(Message request) {
        Integer steamId = Integer.parseInt(request.getContent());

        String username = request.getAuthor()
                .map(User::getUsername)
                .orElseThrow();

        Member newMember = Member.builder().discordUsername(username).steamId(steamId).build();

        return repository
                .findByDiscordUsername(username)
                .filter(Objects::isNull)
                .flatMap(member -> repository.save(newMember));
    }
}

