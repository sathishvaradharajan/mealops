package com.meal.meal_ops.repository;

import com.meal.meal_ops.entity.PaymentConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentConfigRepository
        extends JpaRepository<PaymentConfig, Long> {
    PaymentConfig findTopByOrderByIdAsc();

}

