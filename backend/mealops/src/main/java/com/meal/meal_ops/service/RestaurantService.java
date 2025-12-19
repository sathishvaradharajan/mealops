package com.meal.meal_ops.service;

import com.meal.meal_ops.entity.Restaurant;

import java.util.List;

public interface RestaurantService {
    List<Restaurant> getRestaurantsForUser(String role, String country);
}
