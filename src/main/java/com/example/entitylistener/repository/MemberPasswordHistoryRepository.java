package com.example.entitylistener.repository;

import com.example.entitylistener.entity.MemberPasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberPasswordHistoryRepository extends JpaRepository<MemberPasswordHistory, Long> {
}
