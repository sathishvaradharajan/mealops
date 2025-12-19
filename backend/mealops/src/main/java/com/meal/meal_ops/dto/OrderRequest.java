package com.meal.meal_ops.dto;

import com.meal.meal_ops.entity.PaymentMethod;
import java.util.List;

public class OrderRequest {

    private Long restaurantId;
    private PaymentMethod paymentMethod;
    private List<OrderItemRequest> items;

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}
