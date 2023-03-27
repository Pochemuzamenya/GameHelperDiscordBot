package org.filatov.service.db;

import com.austinv11.servicer.Service;
import lombok.RequiredArgsConstructor;
import org.filatov.model.Member;
import org.filatov.repo.MemberRepository;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MemberService implements DBService<Member> {

    private final MemberRepository repo;

    @Override
    public Mono<Member> create(Member member) {
        return repo.save(member);
    }

    @Override
    public Mono<Member> getByDiscordName(String name) {
        return repo.findByDiscordUsername(name);
    }

    @Override
    public Mono<Member> update(Member member) {
        return repo.save(member);
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return repo.deleteById(id);
    }
}
