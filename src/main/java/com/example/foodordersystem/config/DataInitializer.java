package com.example.foodordersystem.config;

import com.example.foodordersystem.model.entity.MenuItem;
import com.example.foodordersystem.model.entity.User;
import com.example.foodordersystem.repository.MenuItemRepository;
import com.example.foodordersystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

   @Autowired
   private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() == 0) {
            createTestUsers();
        }

        if (menuItemRepository.count() == 0) {
            createMenuItems();
        }
    }

    private void createTestUsers() {

        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@restaurant.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(User.Role.ADMIN);
        userRepository.save(admin);


        User staff = new User();
        staff.setUsername("staff");
        staff.setEmail("staff@restaurant.com");
        staff.setPassword(passwordEncoder.encode("staff123"));
        staff.setRole(User.Role.STAFF);
        userRepository.save(staff);


        User customer = new User();
        customer.setUsername("customer");
        customer.setEmail("customer@test.com");
        customer.setPassword(passwordEncoder.encode("customer123"));
        customer.setRole(User.Role.CUSTOMER);
        userRepository.save(customer);

        System.out.println("Test users created successfully");
    }

    private void createMenuItems() {
        MenuItem[] pizzaItems = {
                new MenuItem("Margherita Pizza", "Classic tomato sauce and fresh mozzarella",
                        new BigDecimal("12.99"), "Pizza"),
                new MenuItem("Pepperoni Pizza", "Tomato sauce, mozzarella, and spicy pepperoni",
                        new BigDecimal("14.99"), "Pizza"),
                new MenuItem("Vegetarian Pizza", "Mixed vegetables with tomato sauce and cheese",
                        new BigDecimal("13.99"), "Pizza")
        };


        MenuItem[] saladItems = {
                new MenuItem("Caesar Salad", "Fresh romaine lettuce with Caesar dressing and croutons",
                        new BigDecimal("8.99"), "Salad"),
                new MenuItem("Greek Salad", "Tomatoes, cucumbers, olives, and feta cheese",
                        new BigDecimal("9.99"), "Salad")
        };


        MenuItem[] pastaItems = {
                new MenuItem("Spaghetti Carbonara", "Creamy pasta with bacon and parmesan cheese",
                        new BigDecimal("14.99"), "Pasta"),
                new MenuItem("Fettuccine Alfredo", "Fettuccine pasta with creamy Alfredo sauce",
                        new BigDecimal("13.99"), "Pasta")
        };


        Arrays.stream(pizzaItems).forEach(menuItemRepository::save);
        Arrays.stream(saladItems).forEach(menuItemRepository::save);
        Arrays.stream(pastaItems).forEach(menuItemRepository::save);

        System.out.println("Menu items created successfully");
    }
}