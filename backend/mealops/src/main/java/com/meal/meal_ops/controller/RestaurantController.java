package com.meal.meal_ops.controller;

import com.meal.meal_ops.dto.MenuItemRequest;
import com.meal.meal_ops.entity.MenuItem;
import com.meal.meal_ops.entity.Restaurant;
import com.meal.meal_ops.entity.User;
import com.meal.meal_ops.repository.UserRepository;
import com.meal.meal_ops.service.MenuService;
import com.meal.meal_ops.service.RestaurantService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final UserRepository userRepository;

    public RestaurantController(RestaurantService restaurantService,
                                MenuService menuService, UserRepository userRepository) {
        this.restaurantService = restaurantService;
        this.menuService = menuService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Restaurant> viewRestaurants(Authentication authentication) {

        String role = authentication.getAuthorities()
                .iterator().next()
                .getAuthority()
                .replace("ROLE_", "");

        String country = (String) authentication.getDetails();

        return restaurantService.getRestaurantsForUser(role, country);
    }

    @PostMapping("/{restaurantId}/menu")
    public MenuItem addMenuItem(
            @PathVariable Long restaurantId,
            @RequestBody MenuItemRequest request,
            Authentication authentication
    ) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow();

        return menuService.addMenuItem(restaurantId, request, user);
    }

    @GetMapping("/{restaurantId}/menu")
    public List<MenuItem> viewMenu(@PathVariable Long restaurantId) {
        return menuService.getMenuByRestaurant(restaurantId);
    }

}
