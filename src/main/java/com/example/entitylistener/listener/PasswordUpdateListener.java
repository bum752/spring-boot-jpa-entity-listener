package com.example.entitylistener.listener;

import com.example.entitylistener.entity.Member;
import com.example.entitylistener.entity.MemberPasswordHistory;
import com.example.entitylistener.repository.MemberPasswordHistoryRepository;
import com.example.entitylistener.utils.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Transactional
public class PasswordUpdateListener {

    @PostLoad
    public void postLoad(Member member) {
        member.setPrePassword(member.getPassword());
    }

    @PrePersist
    public void prePersist(Member member) {
        if (member.getPrePassword() == null) {
            updatePassword(member);
        }
    }

    @PreUpdate
    public void preUpdate(Member member) {
        if (!member.getPrePassword().equals(member.getPassword())) {
            updatePassword(member);
        }
    }

    private void updatePassword(Member member) {
        LocalDateTime now = LocalDateTime.now();
        member.setPasswordUpdatedAt(now);

        MemberPasswordHistoryRepository memberPasswordHistoryRepository =
                BeanUtils.getBean(MemberPasswordHistoryRepository.class);

        MemberPasswordHistory memberPasswordHistory = MemberPasswordHistory.builder()
                .member(member)
                .beforePassword(member.getPrePassword())
                .afterPassword(member.getPassword())
                .build();
        memberPasswordHistoryRepository.save(memberPasswordHistory);
    }

}
