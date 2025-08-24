package com.example.pro.service;

import com.example.pro.model.ShopServices;
import com.example.pro.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository srepo;

    public ResponseEntity<Object> addService(ShopServices service) {
        ShopServices savedService = srepo.save(service);
        return ResponseEntity.ok(savedService);
    }

    public ResponseEntity<List<ShopServices>> getServicesByShop(Long shopId) {
        List<ShopServices> services = srepo.findByShopId(shopId);
        return ResponseEntity.ok(services);
    }

    public ResponseEntity<Object> updateService(Long serviceId, ShopServices updatedService, Long requestingProviderId) {
        Optional<ShopServices> optional = srepo.findById(serviceId);
        if (optional.isEmpty()) {
            return ResponseEntity.status(404).body("Service not found");
        }
        ShopServices service = optional.get();
        if (!service.getShop().getProvider().getId().equals(requestingProviderId)) {
            return ResponseEntity.status(403).body("You are not authorized to update this service");
        }
        service.setTitle(updatedService.getTitle());
        service.setDescription(updatedService.getDescription());
        service.setPrice(updatedService.getPrice());
        service.setDuration(updatedService.getDuration());
        service.setDate(updatedService.getDate());
        return ResponseEntity.ok(srepo.save(service));
    }

    public ResponseEntity<Object> deleteService(Long serviceId, Long requestingProviderId) {
        Optional<ShopServices> optional = srepo.findById(serviceId);
        if (optional.isEmpty()) {
            return ResponseEntity.status(404).body("Service not found");
        }
        ShopServices service = optional.get();
        if (service.getShop() == null) {
            return ResponseEntity.status(500).body("Service's shop not set");
        }
        if (service.getShop().getProvider() == null) {
            return ResponseEntity.status(500).body("Shop's provider not set");
        }
        if (!service.getShop().getProvider().getId().equals(requestingProviderId)) {
            return ResponseEntity.status(403).body("You are not authorized to delete this service");
        }
        try {
            srepo.deleteById(serviceId);
            return ResponseEntity.ok("Service deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting service: " + e.getMessage());
        }
    }

}
