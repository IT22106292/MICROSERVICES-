package com.slit.itemservice;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/items")
public class ItemController {
    
    // Simple in-memory list (no database needed)
    private List<Item> items = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1);
    
    public ItemController() {
        // Initialize with some sample items
        items.add(new Item(idCounter.getAndIncrement(), "Book"));
        items.add(new Item(idCounter.getAndIncrement(), "Laptop"));
        items.add(new Item(idCounter.getAndIncrement(), "Phone"));
    }
    
    @GetMapping
    public List<Item> getItems() {
        return items;
    }
    
    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody CreateItemRequest request) {
        Item newItem = new Item(idCounter.getAndIncrement(), request.getName());
        items.add(newItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable int id) {
        return items.stream()
            .filter(item -> item.getId() == id)
            .findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // Data classes
    public static class Item {
        private int id;
        private String name;
        
        public Item() {}
        
        public Item(int id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
    
    public static class CreateItemRequest {
        private String name;
        
        public CreateItemRequest() {}
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}