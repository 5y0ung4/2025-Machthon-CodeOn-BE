package com.codeon.sweet_choice.repository;

import com.codeon.sweet_choice.entity.SugarRecord;
import com.codeon.sweet_choice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SugarRecordRepository extends JpaRepository<SugarRecord,Long> {
    List<SugarRecord> findAllByUserAndRecordDateBetween(User user, LocalDateTime start, LocalDateTime end);
}
