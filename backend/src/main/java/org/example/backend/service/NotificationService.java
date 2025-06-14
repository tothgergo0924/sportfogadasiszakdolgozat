package org.example.backend.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.configuration.auth.JwtAuthenticationFilter;
import org.example.backend.dto.NotificationCreateDTO;
import org.example.backend.dto.NotificationDTO;
import org.example.backend.dto.NotificationUpdateDTO;
import org.example.backend.mapper.NotificationMapper;
import org.example.backend.model.notification.Notification;
import org.example.backend.model.rel_notification_user.RelNotificationUser;
import org.example.backend.model.user.User;
import org.example.backend.repository.NotificationRepository;
import org.example.backend.repository.RelNotificationUserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private RelNotificationUserRepository relNotificationUserRepository;

    public List<NotificationDTO> getAllNotifications() {
        return notificationMapper.toDtoList(notificationRepository.findAll());
    }

    @Transactional
    public NotificationDTO updateNotification(Long id, NotificationUpdateDTO notificationUpdateDTO) {

        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setContent(notificationUpdateDTO.getContent());
        Notification savedNotification=notificationRepository.save(notification);

        return notificationMapper.toDto(savedNotification);
    }

    @Transactional
    public void deleteNotification(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Notification not found for deletion"));
        notificationRepository.deleteById(notification.getId());
    }

    //TODO: Websocketen kiküldeni az értesítést!

    @Transactional
    public NotificationDTO createNotificationForCertainUsers(NotificationCreateDTO request) {
        Notification newNotification=new Notification();

        newNotification.setContent(request.getContent());
        Notification savedNotification=notificationRepository.saveAndFlush(newNotification);

        List<RelNotificationUser> relList = new ArrayList<>();

        for (String username : request.getUsernames()) {
            RelNotificationUser rel = new RelNotificationUser();
            rel.setUser(userService.getUserByUsername(username));
            rel.setNotification(savedNotification);
            relList.add(rel);
        }

        relNotificationUserRepository.saveAll(relList);
        relNotificationUserRepository.flush();

        savedNotification.setUserNotifications(new ArrayList<>());
        savedNotification.getUserNotifications().addAll(relList);

        return notificationMapper.toDto(savedNotification);

    }

    public NotificationDTO createNotificationForAll(NotificationUpdateDTO request) {
        Notification newNotification=new Notification();

        newNotification.setContent(request.getContent());
        Notification savedNotification=notificationRepository.save(newNotification);
        List<RelNotificationUser> relList = new ArrayList<>();
        userService.getAllUsers().stream().forEach(user -> {
            RelNotificationUser relNotificationUser=new RelNotificationUser();

            relNotificationUser.setUser(user);
            relNotificationUser.setNotification(savedNotification);

            relList.add(relNotificationUser);
        });

        relNotificationUserRepository.saveAll(relList);
        relNotificationUserRepository.flush();

        savedNotification.setUserNotifications(new ArrayList<>());
        savedNotification.getUserNotifications().addAll(relList);

        return notificationMapper.toDto(savedNotification);
    }
}
