package com.meal.meal_ops.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meal.meal_ops.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByRestaurantId(Long restaurantId);
}
