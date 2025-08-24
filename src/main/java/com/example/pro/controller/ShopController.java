package com.example.pro.controller;

import com.example.pro.model.Shop;
import com.example.pro.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/shops")
@CrossOrigin("*")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @PostMapping("/add")
    public ResponseEntity<?> addShop(@RequestBody Shop shop) {
        return shopService.addShop(shop);
    }
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<Object> getShop(@PathVariable Long providerId) {
        return shopService.getShopByProvider(providerId);
    }
   @PutMapping("/{shopId}")
    public ResponseEntity<Object> updateShop(
            @PathVariable Long shopId,
            @RequestBody Shop updatedShop) {
        Long providerId = updatedShop.getProvider().getId(); 
        return shopService.updateShop(shopId, updatedShop, providerId);
    }
    @GetMapping
    public ResponseEntity<?> getAllShops() {
        try {
            return ResponseEntity.ok(shopService.getAllShops());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching shops");
        }
    }
    @PostMapping("/{shopId}/upload-image")
    public ResponseEntity<?> uploadImage(@PathVariable Long shopId,
                                        @RequestParam Long providerId,
                                        @RequestParam("file") MultipartFile file) {
        return shopService.uploadShopImage(shopId, providerId, file);
    }
    @DeleteMapping("/{shopId}/delete-image")
    public ResponseEntity<?> deleteImage(
            @PathVariable Long shopId,
            @RequestParam Long providerId,
            @RequestParam String imageName) {
        return shopService.deleteShopImage(shopId, providerId, imageName);
    }
}
