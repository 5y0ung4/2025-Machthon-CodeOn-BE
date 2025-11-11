package com.codeon.sweet_choice.repository;

import com.codeon.sweet_choice.entity.Food;
import com.codeon.sweet_choice.entity.SugarContain;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SugarContainRepository extends CrudRepository<SugarContain, Long> {
    List<SugarContain> findByFoodId(Food foodId);

    @Query("""
    SELECT DISTINCT sc.foodId
    FROM SugarContain sc
    JOIN sc.sugarId s
    WHERE LOWER(s.sugarNameKR) LIKE LOWER(CONCAT('%', :sugarName, '%'))
       OR LOWER(s.sugarNameEN) LIKE LOWER(CONCAT('%', :sugarName, '%'))
""")
    List<Food> findFoodsBySugarName(@Param("sugarName") String sugarName);

}
