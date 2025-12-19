package com.meal.meal_ops.config;

import com.meal.meal_ops.entity.*;
import com.meal.meal_ops.repository.PaymentConfigRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.meal.meal_ops.repository.UserRepository;
import com.meal.meal_ops.repository.RestaurantRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(
            UserRepository userRepository,
            RestaurantRepository restaurantRepository,
            org.springframework.security.crypto.password.PasswordEncoder encoder
    ) {
        return args -> {

            if (userRepository.count() == 0) {

                userRepository.save(createUser("nick", "password123", Role.ADMIN, "GLOBAL", encoder));
                userRepository.save(createUser("cmarvel", "password123", Role.MANAGER, "India", encoder));
                userRepository.save(createUser("camerica", "password123", Role.MANAGER, "America", encoder));
                userRepository.save(createUser("thanos", "password123", Role.MEMBER, "India", encoder));
                userRepository.save(createUser("thor", "password123", Role.MEMBER, "India", encoder));
                userRepository.save(createUser("travis", "password123", Role.MEMBER, "America", encoder));
            }

            if (restaurantRepository.count() == 0) {
                restaurantRepository.save(createRestaurant("Spice India", "India"));
                restaurantRepository.save(createRestaurant("Burger Town", "America"));
            }
        };
    }

    private User createUser(String username, String rawPassword, Role role, String country,
                            org.springframework.security.crypto.password.PasswordEncoder encoder) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(rawPassword));
        user.setRole(role);
        user.setCountry(country);
        return user;
    }

    private Restaurant createRestaurant(String name, String country) {
        Restaurant r = new Restaurant();
        r.setName(name);
        r.setCountry(country);
        return r;
    }

    @Bean
    CommandLineRunner loadPaymentConfig(PaymentConfigRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                PaymentConfig config = new PaymentConfig();
                config.setDefaultMethod(PaymentMethod.UPI);
                repo.save(config);
            }
        };
    }

}

