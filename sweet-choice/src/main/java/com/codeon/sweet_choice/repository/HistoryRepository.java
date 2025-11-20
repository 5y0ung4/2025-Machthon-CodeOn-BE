package com.codeon.sweet_choice.repository;

import com.codeon.sweet_choice.entity.AnalyzeReport;
import com.codeon.sweet_choice.entity.History;
import com.codeon.sweet_choice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    boolean existsByUserIdAndReportId(User user, AnalyzeReport report);

    void deleteByUserIdAndReportId(User user, AnalyzeReport report);

    List<History> findAllByUserId(User user);
}
