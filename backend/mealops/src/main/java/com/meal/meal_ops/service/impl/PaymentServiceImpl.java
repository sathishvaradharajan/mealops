package com.meal.meal_ops.service.impl;

import com.meal.meal_ops.entity.Order;
import com.meal.meal_ops.entity.PaymentConfig;
import com.meal.meal_ops.entity.PaymentMethod;
import com.meal.meal_ops.repository.PaymentConfigRepository;
import com.meal.meal_ops.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentConfigRepository paymentConfigRepository;

    public PaymentServiceImpl(PaymentConfigRepository paymentConfigRepository) {
        this.paymentConfigRepository = paymentConfigRepository;
    }
    @Override
    public void processPayment(Order order) {
        // Simulate payment gateway success
        // UPI / CARD / CASH
    }
    @Override
    public PaymentConfig updateDefaultPayment(PaymentMethod method) {
        PaymentConfig config = paymentConfigRepository.findById(1L)
                .orElse(new PaymentConfig());

        config.setDefaultMethod(method);
        return paymentConfigRepository.save(config);
    }

    public PaymentConfig getDefaultPayment() {
        return paymentConfigRepository.findTopByOrderByIdAsc();
    }

}


