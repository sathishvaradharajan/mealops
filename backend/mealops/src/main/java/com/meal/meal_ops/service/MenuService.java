package com.meal.meal_ops.service;

import java.util.List;

import com.meal.meal_ops.dto.MenuItemRequest;
import com.meal.meal_ops.entity.MenuItem;
import com.meal.meal_ops.entity.User;

public interface MenuService {

    MenuItem addMenuItem(Long restaurantId, MenuItemRequest request, User user);

    List<MenuItem> getMenuByRestaurant(Long restaurantId);

}