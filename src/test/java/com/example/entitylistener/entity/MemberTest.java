package com.example.entitylistener.entity;

import com.example.entitylistener.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(value = SpringRunner.class)
@SpringBootTest
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    private Member member;

    @Before
    public void setUp() {
        member = memberRepository.save(
                Member.builder()
                        .username("test_user")
                        .password("p@ssw0rd")
                        .build()
        );
    }

    @Test
    public void 비밀번호_변경_테스트() {
        LocalDateTime dateTimeBeforeUpdatePassword = member.getPasswordUpdatedAt();
        member.setPassword("updateP@ssw0rd");
        member = memberRepository.save(member);
        Assertions.assertThat(dateTimeBeforeUpdatePassword).isBefore(member.getPasswordUpdatedAt());
    }

}