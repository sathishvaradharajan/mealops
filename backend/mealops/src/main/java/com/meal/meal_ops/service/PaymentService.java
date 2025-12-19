package com.meal.meal_ops.service;

import com.meal.meal_ops.entity.Order;
import com.meal.meal_ops.entity.PaymentConfig;
import com.meal.meal_ops.entity.PaymentMethod;

public interface PaymentService {
    void processPayment(Order order);

    PaymentConfig updateDefaultPayment(PaymentMethod method);

    PaymentConfig getDefaultPayment();
}

