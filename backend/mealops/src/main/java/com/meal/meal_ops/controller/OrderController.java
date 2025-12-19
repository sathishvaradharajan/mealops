package com.meal.meal_ops.controller;

import com.meal.meal_ops.dto.OrderRequest;
import com.meal.meal_ops.dto.OrderResponse;
import com.meal.meal_ops.entity.*;
import com.meal.meal_ops.repository.PaymentConfigRepository;
import com.meal.meal_ops.repository.UserRepository;
import com.meal.meal_ops.service.PaymentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.meal.meal_ops.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;
    private final PaymentService paymentService;
    public OrderController(OrderService orderService, UserRepository userRepository, PaymentService paymentService) {
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.paymentService = paymentService;
    }


    @PostMapping("/create")
    public OrderResponse createOrder(
            @RequestBody OrderRequest request,
            Authentication authentication
    ) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        Order order = orderService.createOrder(request, user);
        return orderService.mapToResponse(order);
    }

    @PostMapping("/{orderId}/checkout")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public OrderResponse checkoutOrder(
            @PathVariable Long orderId,
            Authentication authentication
    ) {
        User user = userRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderService.checkoutOrder(orderId, user);
        return orderService.mapToResponse(order);
    }

    @PostMapping("/cancel/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public OrderResponse cancelOrder(
            @PathVariable Long orderId,
            Authentication authentication
    ) {
        User user = userRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderService.cancelOrder(orderId, user);
        return orderService.mapToResponse(order);
    }

    @PutMapping("/payment/default")
    public PaymentConfig updateDefaultPayment(
            @RequestParam PaymentMethod method,
            Authentication authentication
    ) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow();

        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admin can update default payment method");
        }
        
        return paymentService.updateDefaultPayment(method);
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','MEMBER')")
    public OrderResponse getOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return orderService.mapToResponse(order);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','MEMBER')")
    public List<OrderResponse> getOrders(Authentication authentication) {

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow();

        return orderService.getOrdersForUser(user);
    }

    @GetMapping("/payment/default")
    @PreAuthorize("hasRole('ADMIN')")
    public PaymentConfig getDefaultPayment() {
        return paymentService.getDefaultPayment();
    }

}
