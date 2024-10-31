package com.reacconmind.reacconmind.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reacconmind.reacconmind.dto.NotificationDTO;
import com.reacconmind.reacconmind.model.AlertNotificationStrategy;
import com.reacconmind.reacconmind.model.CommentNotificationStrategy;
import com.reacconmind.reacconmind.model.FollowNotificationStrategy;
import com.reacconmind.reacconmind.model.LikeNotificationStrategy;
import com.reacconmind.reacconmind.model.MessageNotificationStrategy;
import com.reacconmind.reacconmind.model.Notification;
import com.reacconmind.reacconmind.model.NotificationStatus;
import com.reacconmind.reacconmind.model.TypeNotification;
import com.reacconmind.reacconmind.repository.NotificationRepository;
import com.reacconmind.reacconmind.repository.NotificationStrategy;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class NotificationService {
    private final Map<TypeNotification, NotificationStrategy> strategies = new HashMap<>();
    @Autowired
    private NotificationRepository repository;

    public NotificationService(LikeNotificationStrategy likeNotificationStrategy,
            MessageNotificationStrategy messageNotificationStrategy,
            FollowNotificationStrategy followNotificationStrategy,
            CommentNotificationStrategy commentNotificationStrategy,
            AlertNotificationStrategy alertNotificationStrategy) {
        strategies.put(TypeNotification.Like, likeNotificationStrategy);
        strategies.put(TypeNotification.Message, messageNotificationStrategy);
        strategies.put(TypeNotification.Follow, followNotificationStrategy);
        strategies.put(TypeNotification.Comment, commentNotificationStrategy);
        strategies.put(TypeNotification.Alert, alertNotificationStrategy);
    }

    public List<NotificationDTO> getAll() {
        List<Notification> notifications = repository.findAll();
        return notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Notification getByIdNotification(Integer idNotification) {
        return repository.findById(idNotification).get();
    }

    public void save(Notification notification) {
        repository.save(notification);
    }

    public void delete(Integer idNotification) {
        repository.deleteById(idNotification);
    }

    public void maskAsRead(Integer idNotification) {
        Optional<Notification> notificacionOpt = repository.findById(idNotification);
        if (notificacionOpt.isPresent()) {
            Notification notificacion = notificacionOpt.get();
            notificacion.setState(NotificationStatus.Read);
            repository.save(notificacion);
        } else {
            throw new NoSuchElementException("Notification not found");
        }
    }

    public List<Notification> getUnreadNotifications(Integer userId) {
        return repository.findAll().stream()
                .filter(notification -> {
                    boolean isUnread = notification.getState() == NotificationStatus.Unread;
                    boolean isForUser = notification.getIdUser() != null && notification.getIdUser().getIdUser() == userId;
                    // Esto te ayudará a ver qué filtros están fallando
                    System.out.println("Notification ID: " + notification.getIdNotification() + 
                                       " | Unread: " + isUnread + 
                                       " | For User: " + isForUser + 
                                       " | User ID in Notification: " + (notification.getIdUser() != null ? notification.getIdUser().getIdUser() : "null"));
                    return isUnread && isForUser;
                })
                .collect(Collectors.toList());
    }

    public void sendNotification(Notification notification) {
        NotificationStrategy strategy = strategies.get(notification.getTypeNotification());
        if (strategy != null) {
            strategy.send(notification);
            repository.save(notification);
        } else {
            throw new IllegalArgumentException("Unknow notification type");
        }

    }

    public NotificationDTO convertToDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setIdNotification(notification.getIdNotification());
        dto.setIdUser(notification.getIdUser());
        dto.setTypeNotification(notification.getTypeNotification());
        dto.setContent(notification.getContent());
        dto.setState(notification.getState());
        dto.setDateNotification(Date.valueOf(LocalDate.now()));
        return dto;
    }

    public Notification convertToEntity(NotificationDTO notificationDTO) {
        Notification notification = new Notification();
        notification.setIdNotification(notificationDTO.getIdNotification());
        notification.setIdUser(notificationDTO.getIdUser());
        notification.setTypeNotification(notificationDTO.getTypeNotification());
        notification.setContent(notificationDTO.getContent());
        notification.setState(notificationDTO.getState());
        return notification;
    }

}
