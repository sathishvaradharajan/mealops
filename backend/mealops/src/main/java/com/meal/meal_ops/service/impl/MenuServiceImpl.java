package com.meal.meal_ops.service.impl;

import com.meal.meal_ops.dto.MenuItemRequest;
import com.meal.meal_ops.entity.MenuItem;
import com.meal.meal_ops.entity.Restaurant;
import com.meal.meal_ops.entity.Role;
import com.meal.meal_ops.entity.User;
import com.meal.meal_ops.repository.MenuItemRepository;
import com.meal.meal_ops.repository.RestaurantRepository;
import com.meal.meal_ops.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuItemRepository menuRepo;
    private final RestaurantRepository restaurantRepo;

    public MenuServiceImpl(MenuItemRepository menuRepo,
                           RestaurantRepository restaurantRepo) {
        this.menuRepo = menuRepo;
        this.restaurantRepo = restaurantRepo;
    }

    @Override
    public MenuItem addMenuItem(Long restaurantId, MenuItemRequest request, User user) {

        if (user.getRole() == Role.MEMBER) {
            throw new RuntimeException("Members cannot add menu items");
        }

        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        // MANAGER → same country only
        if (user.getRole() == Role.MANAGER &&
                !restaurant.getCountry().equalsIgnoreCase(user.getCountry())) {
            throw new RuntimeException("Cannot add menu to another country restaurant");
        }

        MenuItem item = new MenuItem();
        item.setName(request.getName());
        item.setPrice(request.getPrice());
        item.setRestaurant(restaurant);

        return menuRepo.save(item);
    }

    @Override
    public List<MenuItem> getMenuByRestaurant(Long restaurantId) {
        return menuRepo.findByRestaurantId(restaurantId);
    }
}


