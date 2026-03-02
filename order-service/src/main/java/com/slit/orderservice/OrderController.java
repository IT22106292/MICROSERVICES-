package com.slit.orderservice;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    private List<Map<String, Object>> orders = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1);
    
    public OrderController() {
        // Initialize with sample orders
        Map<String, Object> order1 = new ConcurrentHashMap<>();
        order1.put("id", idCounter.getAndIncrement());
        order1.put("item", "Laptop");
        order1.put("quantity", 1);
        order1.put("customerId", "C001");
        order1.put("status", "PENDING");
        orders.add(order1);
        
        Map<String, Object> order2 = new ConcurrentHashMap<>();
        order2.put("id", idCounter.getAndIncrement());
        order2.put("item", "Book");
        order2.put("quantity", 3);
        order2.put("customerId", "C002");
        order2.put("status", "CONFIRMED");
        orders.add(order2);
    }
    
    @GetMapping
    public List<Map<String, Object>> getOrders() {
        return orders;
    }
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> placeOrder(@RequestBody Map<String, Object> order) {
        order.put("id", idCounter.getAndIncrement());
        order.put("status", "PENDING");
        orders.add(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable int id) {
        return orders.stream()
            .filter(order -> order.get("id").equals(id))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}