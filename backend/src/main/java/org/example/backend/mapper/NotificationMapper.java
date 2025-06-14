package org.example.backend.mapper;

import org.example.backend.configuration.MapstructConfig;
import org.example.backend.dto.NotificationDTO;
import org.example.backend.model.notification.Notification;
import org.example.backend.model.rel_notification_user.RelNotificationUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = MapstructConfig.class)
public interface NotificationMapper {

    @Mapping(target = "usernames",expression = "java(mapUsernames(notification.getUserNotifications()))")
    NotificationDTO toDto(Notification notification);

    List<NotificationDTO> toDtoList(List<Notification> notifications);

    default List<String> mapUsernames(List<RelNotificationUser> rels) {
        if (rels == null) return new ArrayList<>();
        return rels.stream()
                .map(rel -> rel.getUser().getUsername())
                .collect(Collectors.toList());
    }
}
