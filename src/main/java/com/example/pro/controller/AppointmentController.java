package com.example.pro.controller;

import com.example.pro.model.Appointment;
import com.example.pro.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin("*")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<Object> bookAppointment(@RequestBody Appointment appointment) {
        return appointmentService.bookAppointment(appointment);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Appointment>> getUserAppointments(@PathVariable Long userId) {
        return appointmentService.getAppointmentsByUser(userId);
    }

    @GetMapping("/shop/{shopId}")
    public ResponseEntity<List<Appointment>> getShopAppointments(@PathVariable Long shopId) {
        return appointmentService.getAppointmentsByShop(shopId);
    }
    @PutMapping("/{appointmentId}/accept")
    public ResponseEntity<Object> acceptAppointment(@PathVariable Long appointmentId,
                                                    @RequestParam Long providerId) {
        return appointmentService.acceptAppointment(appointmentId, providerId);
    }

    @PutMapping("/{appointmentId}/reject")
    public ResponseEntity<Object> rejectAppointment(@PathVariable Long appointmentId,
                                                    @RequestParam Long providerId) {
        return appointmentService.rejectAppointment(appointmentId, providerId);
    }

    @PutMapping("/{appointmentId}/complete")
    public ResponseEntity<Object> completeAppointment(@PathVariable Long appointmentId,
                                                    @RequestParam Long providerId) {
        return appointmentService.completeAppointment(appointmentId, providerId);
    }

}
