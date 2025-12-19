package com.meal.meal_ops.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.meal.meal_ops.entity.Restaurant;
import com.meal.meal_ops.repository.RestaurantRepository;
import com.meal.meal_ops.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<Restaurant> getRestaurantsForUser(String role, String country) {

        if ("ADMIN".equals(role)) {
            return restaurantRepository.findAll();
        }

        // MANAGER / MEMBER → only their country
        return restaurantRepository.findByCountryIgnoreCase(country);
    }

}
