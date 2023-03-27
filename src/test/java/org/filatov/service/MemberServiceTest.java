package org.filatov.service;

import org.filatov.model.Member;
import org.filatov.service.db.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MemberServiceTest {

    @MockBean
    Member member;

    MemberService service;
    @Test
    void create() {
        service.create(member);
    }

    @Test
    void getByDiscordName() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}