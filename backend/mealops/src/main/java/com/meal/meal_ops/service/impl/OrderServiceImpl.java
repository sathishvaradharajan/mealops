package com.meal.meal_ops.service.impl;

import com.meal.meal_ops.dto.OrderItemRequest;
import com.meal.meal_ops.dto.OrderItemResponse;
import com.meal.meal_ops.dto.OrderRequest;
import com.meal.meal_ops.dto.OrderResponse;
import com.meal.meal_ops.entity.*;
import com.meal.meal_ops.repository.MenuItemRepository;
import com.meal.meal_ops.repository.OrderItemRepository;
import com.meal.meal_ops.repository.OrderRepository;
import com.meal.meal_ops.repository.RestaurantRepository;
import com.meal.meal_ops.service.PaymentService;
import org.springframework.stereotype.Service;

import com.meal.meal_ops.service.OrderService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final MenuItemRepository menuRepo;
    private final RestaurantRepository restaurantRepo;
    private final PaymentService paymentService;

    public OrderServiceImpl(
            OrderRepository orderRepo, OrderItemRepository orderItemRepo,
            MenuItemRepository menuRepo,
            RestaurantRepository restaurantRepo,
            PaymentService paymentService
    ) {
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.menuRepo = menuRepo;
        this.restaurantRepo = restaurantRepo;
        this.paymentService = paymentService;
    }

    @Transactional
    public Order createOrder(OrderRequest request, User user) {

        Restaurant restaurant = restaurantRepo.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Order order = new Order();
        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setStatus(OrderStatus.CREATED);
        order.setPaymentMethod(request.getPaymentMethod());

        // Save order first
        Order savedOrder = orderRepo.save(order);

        double total = 0;

        for (OrderItemRequest itemReq : request.getItems()) {

            MenuItem menuItem = menuRepo.findById(itemReq.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));

            OrderItem oi = new OrderItem();
            oi.setOrder(savedOrder);
            oi.setMenuItem(menuItem);
            oi.setQuantity(itemReq.getQuantity());

            // snapshot price
            oi.setPrice(menuItem.getPrice());

            // calculate total ONLY from snapshot price
            total += oi.getPrice() * oi.getQuantity();

            orderItemRepo.save(oi);
        }

        savedOrder.setTotalAmount(total);
        return orderRepo.save(savedOrder);
    }


    public OrderResponse mapToResponse(Order order) {

        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setRestaurantName(order.getRestaurant().getName());
        response.setStatus(order.getStatus().name());
        response.setPaymentMethod(order.getPaymentMethod().name());
        response.setTotalAmount(order.getTotalAmount());

        List<OrderItemResponse> items = orderItemRepo
                .findByOrderId(order.getId())
                .stream()
                .map(oi -> {
                    OrderItemResponse ir = new OrderItemResponse();
                    ir.setItemName(oi.getMenuItem().getName());
                    ir.setQuantity(oi.getQuantity());
                    ir.setPrice(oi.getPrice());
                    return ir;
                })
                .toList();

        response.setItems(items);
        return response;
    }

    @Override
    @Transactional
    public Order checkoutOrder(Long orderId, User user) {

        if (user.getRole() == Role.MEMBER) {
            throw new RuntimeException("Members are not allowed to checkout");
        }

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // MANAGER → same country only
        if (user.getRole() == Role.MANAGER &&
                !order.getRestaurant().getCountry().equalsIgnoreCase(user.getCountry())) {
            throw new RuntimeException("Cannot checkout order from another country");
        }

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Order cannot be checked out");
        }

        paymentService.processPayment(order);

        order.setStatus(OrderStatus.PAID);
        return orderRepo.save(order);
    }


    @Override
    @Transactional
    public Order cancelOrder(Long orderId, User user) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (user.getRole() == Role.MEMBER) {
            throw new RuntimeException("Members cannot cancel orders");
        }

        if (user.getRole() == Role.MANAGER &&
                !order.getRestaurant().getCountry().equalsIgnoreCase(user.getCountry())) {
            throw new RuntimeException("Cannot cancel order from another country");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order already Cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);
        return orderRepo.save(order);
    }

    @Override
    @Transactional
    public Order updatePaymentMethod(Long orderId, PaymentMethod method, User user) {

        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admin can update payment method");
        }

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Cannot update payment after checkout");
        }

        order.setPaymentMethod(method);
        return orderRepo.save(order);
    }
    public Order getOrderById(Long orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<OrderResponse> getOrdersForUser(User user) {

        List<Order> orders;

        if (user.getRole() == Role.ADMIN) {
            // Admin sees ALL orders
            orders = orderRepo.findAll();
        }
        else if (user.getRole() == Role.MANAGER) {
            // Manager sees orders only from their country
            orders = orderRepo.findByRestaurant_Country(user.getCountry().trim());
        }
        else {
            // Member sees only their own orders
            orders = orderRepo.findByUserId(user.getId());
        }

        return orders.stream()
                .map(this::mapToResponse)
                .toList();
    }


}

