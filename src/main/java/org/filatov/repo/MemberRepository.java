package org.filatov.repo;

import org.filatov.model.Member;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface MemberRepository extends ReactiveCrudRepository<Member, Integer> {

    Mono<Member> findByDiscordUsername(String name);
}
