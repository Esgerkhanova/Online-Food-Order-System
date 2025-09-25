package com.example.foodordersystem.service;

import ch.qos.logback.classic.Logger;
import com.example.foodordersystem.model.dto.request.OrderRequest;
import com.example.foodordersystem.model.dto.response.OrderResponse;
import com.example.foodordersystem.model.entity.MenuItem;
import com.example.foodordersystem.model.entity.Order;
import com.example.foodordersystem.model.entity.OrderItem;
import com.example.foodordersystem.model.entity.User;
import com.example.foodordersystem.repository.MenuItemRepository;
import com.example.foodordersystem.repository.OrderRepository;
import com.example.foodordersystem.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository,
                        MenuItemRepository menuItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public OrderResponse createOrder(OrderRequest orderRequest, String username) {
        // Null yoxlaması əlavə edin
        if (orderRequest.getOrderItems() == null || orderRequest.getOrderItems().isEmpty()) {
            throw new RuntimeException("Order must contain at least one item");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order(user);

        for (var itemRequest : orderRequest.getOrderItems()) {
            // Hər bir item üçün null yoxlaması
            if (itemRequest == null) {
                throw new RuntimeException("Order item cannot be null");
            }

            if (itemRequest.getMenuItemId() == null) {
                throw new RuntimeException("Menu item ID cannot be null");
            }

            MenuItem menuItem = menuItemRepository.findById(itemRequest.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found: " + itemRequest.getMenuItemId()));

            if (!menuItem.isAvailable()) {
                throw new RuntimeException("Menu item not available: " + menuItem.getName());
            }

            if (itemRequest.getQuantity() <= 0) {
                throw new RuntimeException("Quantity must be greater than 0 for: " + menuItem.getName());
            }

            OrderItem orderItem = new OrderItem(menuItem, itemRequest.getQuantity());
            order.addOrderItem(orderItem);
        }

        order.calculateTotalAmount();
        Order savedOrder = orderRepository.save(order);

        return convertToOrderResponse(savedOrder);
    }

    public OrderResponse getOrderById(Long id, String username) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!order.getUser().getId().equals(user.getId()) &&
                user.getRole() == User.Role.CUSTOMER) {
            throw new RuntimeException("Access denied");
        }

        return convertToOrderResponse(order);
    }

    public List<OrderResponse> getUserOrders(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUserId(user.getId()).stream()
                .map(this::convertToOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse updateOrderStatus(Long id, String status, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == User.Role.CUSTOMER) {
            throw new RuntimeException("Access denied");
        }

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        try {
            Order.OrderStatus newStatus = Order.OrderStatus.valueOf(status.toUpperCase());
            order.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid order status: " + status);
        }

        Order updatedOrder = orderRepository.save(order);

        return convertToOrderResponse(updatedOrder);
    }

    public List<OrderResponse> getAllOrders(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == User.Role.CUSTOMER) {
            throw new RuntimeException("Access denied");
        }

        return orderRepository.findAllOrderByOrderDateDesc().stream()
                .map(this::convertToOrderResponse)
                .collect(Collectors.toList());
    }

    private OrderResponse convertToOrderResponse(Order order) {
        if (order == null) {
            throw new RuntimeException("Order cannot be null");
        }

        OrderResponse response = new OrderResponse();
        response.setId(order.getId());

        // User null yoxlaması əlavə edin
        if (order.getUser() != null) {
            response.setCustomerName(order.getUser().getUsername());
        } else {
            response.setCustomerName("Unknown");
            logger.warn("Order has no user associated", order.getId());
        }

        response.setOrderDate(order.getOrderDate());
        response.setStatus(Order.OrderStatus.valueOf(order.getStatus().name()));
        response.setTotalAmount(order.getTotalAmount());

        // Order items-ləri də əlavə edin
        if (order.getOrderItems() != null) {
            List<OrderResponse.OrderItemResponse> itemResponses = order.getOrderItems().stream()
                    .map(this::convertToOrderItemResponse)
                    .collect(Collectors.toList());
            response.setItems(itemResponses);
        } else {
            response.setItems(new ArrayList<>());
        }

        return response;
    }

    // Bu metod çatışmırdı - İndi əlavə edirik
    private OrderResponse.OrderItemResponse convertToOrderItemResponse(OrderItem orderItem) {
        if (orderItem == null) {
            throw new RuntimeException("Order item cannot be null");
        }

        OrderResponse.OrderItemResponse response = new OrderResponse.OrderItemResponse();

        if (orderItem.getMenuItem() != null) {
            response.setId(orderItem.getMenuItem().getId());
            response.setMenuItemName(orderItem.getMenuItem().getName());
        } else {
            response.setId(0L);
            response.setMenuItemName("Unknown Item");
            logger.warn("Order item {} has no menu item associated", orderItem.getId());
        }

        response.setQuantity(orderItem.getQuantity());
        response.setPrice(orderItem.getPrice());
        response.setSubtotal(orderItem.getSubtotal());

        return response;
    }
}