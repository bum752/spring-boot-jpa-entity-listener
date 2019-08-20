package com.example.entitylistener.entity;

import com.example.entitylistener.repository.MemberPasswordHistoryRepository;
import com.example.entitylistener.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(value = SpringRunner.class)
@SpringBootTest
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberPasswordHistoryRepository memberPasswordHistoryRepository;

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

        List<MemberPasswordHistory> memberPasswordHistoryList = memberPasswordHistoryRepository.findAll();
        memberPasswordHistoryList.forEach(System.out::println);
//        expected
//        MemberPasswordHistory(id=1, member=com.example.entitylistener.entity.Member@133000b8, beforePassword=null, afterPassword=p@ssw0rd, createdAt=2019-08-20T12:00:57.575)
//        MemberPasswordHistory(id=2, member=com.example.entitylistener.entity.Member@133000b8, beforePassword=p@ssw0rd, afterPassword=updateP@ssw0rd, createdAt=2019-08-20T12:00:57.639)

        Assertions.assertThat(dateTimeBeforeUpdatePassword).isBefore(member.getPasswordUpdatedAt());
        Assertions.assertThat(memberPasswordHistoryList).hasSize(2);
    }

}