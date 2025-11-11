package com.codeon.sweet_choice.repository;

import com.codeon.sweet_choice.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByFoodNameContaining(String foodName);

    Food findByFoodId(Long foodId);
}
