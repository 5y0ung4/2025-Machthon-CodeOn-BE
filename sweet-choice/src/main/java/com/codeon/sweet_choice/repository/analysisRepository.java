package com.codeon.sweet_choice.repository;

import com.codeon.sweet_choice.entity.AnalyzeReport;
import com.codeon.sweet_choice.entity.Food;
import com.codeon.sweet_choice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface analysisRepository extends JpaRepository<AnalyzeReport, Long> {
    AnalyzeReport findByReportId(Long id);

    AnalyzeReport findByUserIdAndFoodId(User user, Food food);
}
