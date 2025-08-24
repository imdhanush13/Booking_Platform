package com.example.pro.service;

import com.example.pro.model.Appointment;
import com.example.pro.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public ResponseEntity<Object> bookAppointment(Appointment appointment) {
        appointment.setStatus(Appointment.Status.PENDING);
        Appointment saved = appointmentRepository.save(appointment);
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<List<Appointment>> getAppointmentsByUser(Long userId) {
        List<Appointment> appointments = appointmentRepository.findByUserId(userId);
        return ResponseEntity.ok(appointments);
    }

    public ResponseEntity<List<Appointment>> getAppointmentsByShop(Long shopId) {
        List<Appointment> appointments = appointmentRepository.findByService_Shop_Id(shopId);
        return ResponseEntity.ok(appointments);
    }

    public ResponseEntity<Object> acceptAppointment(Long id, Long providerId) {
        Optional<Appointment> optional = appointmentRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(404).body("Appointment not found");
        }
        Appointment appt = optional.get();
        if (!appt.getService().getShop().getProvider().getId().equals(providerId)) {
            return ResponseEntity.status(403).body("Not authorized to update this appointment");
        }
        if (appt.getStatus() != Appointment.Status.PENDING) {
            return ResponseEntity.status(400).body("Only pending appointments can be confirmed");
        }
        appt.setStatus(Appointment.Status.CONFIRMED);
        return ResponseEntity.ok(appointmentRepository.save(appt));
    }

    public ResponseEntity<Object> rejectAppointment(Long id, Long providerId) {
        Optional<Appointment> optional = appointmentRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(404).body("Appointment not found");
        }
        Appointment appt = optional.get();
        if (!appt.getService().getShop().getProvider().getId().equals(providerId)) {
            return ResponseEntity.status(403).body("Not authorized to update this appointment");
        }
        if (appt.getStatus() != Appointment.Status.PENDING) {
            return ResponseEntity.status(400).body("Only pending appointments can be cancelled");
        }
        appt.setStatus(Appointment.Status.CANCELLED);
        return ResponseEntity.ok(appointmentRepository.save(appt));
    }

    public ResponseEntity<Object> completeAppointment(Long id, Long providerId) {
        Optional<Appointment> optional = appointmentRepository.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.status(404).body("Appointment not found");
        }
        Appointment appt = optional.get();
        if (!appt.getService().getShop().getProvider().getId().equals(providerId)) {
            return ResponseEntity.status(403).body("You are not authorized to complete this appointment");
        }
        if (appt.getStatus() != Appointment.Status.CONFIRMED) {
            return ResponseEntity.status(400).body("Only confirmed appointments can be completed");
        }
        appt.setStatus(Appointment.Status.COMPLETED);
        appointmentRepository.save(appt);
        String feedbackLink = "/review.html?appointmentId=" + appt.getId();
        return ResponseEntity.ok("Appointment completed. Feedback link: " + feedbackLink);
    }

}
