package com.example.pro.controller;

import com.example.pro.model.ShopServices;
import com.example.pro.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin("*")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PostMapping("/add")
    public ResponseEntity<Object> addService(@RequestBody ShopServices service) {
        return serviceService.addService(service);
    }

    @GetMapping("/shop/{shopId}")
    public ResponseEntity<List<ShopServices>> getServicesByShop(@PathVariable Long shopId) {
        return serviceService.getServicesByShop(shopId);
    }

    @PutMapping("/update/{serviceId}")
    public ResponseEntity<Object> updateService(@PathVariable Long serviceId,
                                                @RequestBody ShopServices updatedService,
                                                @RequestParam Long providerId) {
        return serviceService.updateService(serviceId, updatedService, providerId);
    }

    @DeleteMapping("/delete/{serviceId}")
    public ResponseEntity<Object> deleteService(@PathVariable Long serviceId,
                                                @RequestParam Long providerId) {
        return serviceService.deleteService(serviceId, providerId);
    }
}
