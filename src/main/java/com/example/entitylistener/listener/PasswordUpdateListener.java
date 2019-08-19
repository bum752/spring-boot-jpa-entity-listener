package com.example.entitylistener.listener;

import com.example.entitylistener.entity.Member;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

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
    }

}
