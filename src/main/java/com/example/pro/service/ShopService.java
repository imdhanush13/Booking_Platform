package com.example.pro.service;

import com.example.pro.model.Shop;
import com.example.pro.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

@Service
public class ShopService {

    @Autowired
    private ShopRepository sprepo;

    public ResponseEntity<?> addShop(Shop shop) {
        if (sprepo.findByProviderId(shop.getProvider().getId()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Provider already has a shop.");
        }
        Shop savedShop = sprepo.save(shop);
        return ResponseEntity.ok(savedShop);
    }
    public ResponseEntity<Object> getShopByProvider(Long providerId) {
    Optional<Shop> shop = sprepo.findByProviderId(providerId);
        if (shop.isPresent()) {
            return ResponseEntity.ok(shop.get());
        } else {
            return ResponseEntity.status(404).body("Shop not found");
        }
    }

    public ResponseEntity<Object> updateShop(Long shopId, Shop updatedShop, Long requestingProviderId) {
    Optional<Shop> existingOpt = sprepo.findById(shopId);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Shop not found");
        }
        Shop existing = existingOpt.get();
        if (!existing.getProvider().getId().equals(requestingProviderId)) {
            return ResponseEntity.status(403).body("You are not authorized to update this shop");
        }
        existing.setName(updatedShop.getName());
        existing.setLocation(updatedShop.getLocation());
        existing.setImages(updatedShop.getImages());
        Shop saved = sprepo.save(existing);
        return ResponseEntity.ok(saved);
    }

    public List<Shop> getAllShops() {
        return sprepo.findAll();
    }

    public ResponseEntity<?> uploadShopImage(Long shopId, Long providerId, MultipartFile file) {
        Optional<Shop> shopOpt = sprepo.findById(shopId);
        if (shopOpt.isEmpty()) return ResponseEntity.status(404).body("Shop not found");
        Shop shop = shopOpt.get();
        if (!shop.getProvider().getId().equals(providerId)) {
            return ResponseEntity.status(403).body("Not authorized");
        }
        try {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get("uploads/" + filename);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            List<String> images = shop.getImages();
            if (images == null) images = new ArrayList<>();
            images.add(filename);
            shop.setImages(images);
            sprepo.save(shop);
            return ResponseEntity.ok(Map.of("url", "/uploads/" + filename));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to upload image");
        }
    }
    public ResponseEntity<?> deleteShopImage(Long shopId, Long providerId, String imageName) {
        Optional<Shop> shopOpt = sprepo.findById(shopId);
        if (shopOpt.isEmpty()) return ResponseEntity.status(404).body("Shop not found");
        Shop shop = shopOpt.get();
        if (!shop.getProvider().getId().equals(providerId)) {
            return ResponseEntity.status(403).body("Not authorized");
        }
        List<String> images = shop.getImages();
        if (images != null && images.remove(imageName)) {
            shop.setImages(images);
            sprepo.save(shop);
            Path path = Paths.get("uploads/" + Paths.get(imageName).getFileName());
            try {
                Files.deleteIfExists(path);
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Failed to delete image file");
            }
            return ResponseEntity.ok("Image deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Image not found in shop");
        }
    }


}

    
   