package com.slit.paymentservice;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    
    private List<Map<String, Object>> payments = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1);
    
    public PaymentController() {
        // Initialize with sample payments
        Map<String, Object> payment1 = new ConcurrentHashMap<>();
        payment1.put("id", idCounter.getAndIncrement());
        payment1.put("orderId", 1);
        payment1.put("amount", 1299.99);
        payment1.put("method", "CARD");
        payment1.put("status", "SUCCESS");
        payments.add(payment1);
        
        Map<String, Object> payment2 = new ConcurrentHashMap<>();
        payment2.put("id", idCounter.getAndIncrement());
        payment2.put("orderId", 2);
        payment2.put("amount", 45.50);
        payment2.put("method", "PAYPAL");
        payment2.put("status", "PENDING");
        payments.add(payment2);
    }
    
    @GetMapping
    public List<Map<String, Object>> getPayments() {
        return payments;
    }
    
    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody Map<String, Object> payment) {
        payment.put("id", idCounter.getAndIncrement());
        payment.put("status", "SUCCESS");
        payments.add(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getPayment(@PathVariable int id) {
        return payments.stream()
            .filter(p -> p.get("id").equals(id))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}