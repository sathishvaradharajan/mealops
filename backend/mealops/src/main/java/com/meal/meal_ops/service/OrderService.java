package com.meal.meal_ops.service;

import com.meal.meal_ops.dto.OrderRequest;
import com.meal.meal_ops.dto.OrderResponse;
import com.meal.meal_ops.entity.Order;
import com.meal.meal_ops.entity.PaymentMethod;
import com.meal.meal_ops.entity.User;

import java.util.List;

public interface OrderService {

    Order createOrder(OrderRequest request, User user);

    Order checkoutOrder(Long orderId, User user);

    Order cancelOrder(Long orderId, User user);

    OrderResponse mapToResponse(Order order);

    Order updatePaymentMethod(Long orderId, PaymentMethod method, User user);

    Order getOrderById(Long orderId);

    List<OrderResponse> getOrdersForUser(User user);
}
