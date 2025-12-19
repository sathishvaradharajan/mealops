package com.meal.meal_ops.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Setter;

@Entity
public class PaymentConfig {

    @Setter
    @Id
    private Long id = 1L;

    @Enumerated(EnumType.STRING)
    private PaymentMethod defaultMethod;

    public Long getId() {
        return id;
    }

    public PaymentMethod getDefaultMethod() {
        return defaultMethod;
    }

    public void setDefaultMethod(PaymentMethod method) {
        this.defaultMethod=method;
    }
}
