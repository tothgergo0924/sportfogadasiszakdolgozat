package org.example.backend.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.backend.dto.NotificationCreateDTO;
import org.example.backend.dto.NotificationDTO;
import org.example.backend.dto.NotificationUpdateDTO;
import org.example.backend.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/notifications")
@Validated
@PreAuthorize("hasAuthority('ADMIN')")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationDTO> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationDTO> updateNotification(@PathVariable Long id, @RequestBody NotificationUpdateDTO notificationUpdateDTO) {
        return ResponseEntity.ok(notificationService.updateNotification(id, notificationUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> updateNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok("Deletion successful");
    }

    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@Valid @RequestBody NotificationCreateDTO request){
        return ResponseEntity.ok(notificationService.createNotificationForCertainUsers(request));
    }

    @PostMapping("/all")
    public ResponseEntity<NotificationDTO> createNotificationForAll(@RequestBody NotificationUpdateDTO request){
        return ResponseEntity.ok(notificationService.createNotificationForAll(request));
    }
}