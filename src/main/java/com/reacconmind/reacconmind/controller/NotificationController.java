package com.reacconmind.reacconmind.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reacconmind.reacconmind.dto.NotificationDTO;
import com.reacconmind.reacconmind.model.Notification;
import com.reacconmind.reacconmind.model.TypeNotification;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.service.NotificationService;
import com.reacconmind.reacconmind.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Notification", description = "Provides methods for managing notifications")
@RequestMapping("notifications")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
                RequestMethod.PUT })
public class NotificationController {

        @Autowired
        private NotificationService notificationService;

        @Autowired
        private UserService userService;

        @Operation(summary = "Get all Notifications for a user with pagination", description = "Gets a list of all registered notifications for a user.")
        @ApiResponse(responseCode = "200", description = "List of notifications obtained correctly", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Notification.class))) })
        @GetMapping("/{userId}")
        public ResponseEntity<List<NotificationDTO>> getAllNotificationsByUserId(
                        @PathVariable Integer userId,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size) {

                List<NotificationDTO> notifications = notificationService.getAllNotificationsByUserId(userId, page,
                                size);
                return ResponseEntity.ok(notifications);
        }

        @Operation(summary = "Mark a notification as read", description = "Mark a notification as read.")
        @ApiResponse(responseCode = "200", description = "Notification read correctly", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Notification.class))) })
        @PutMapping("/read/{idNotification}")
        public ResponseEntity<Void> maskAsRead(@PathVariable Integer idNotification) {
                notificationService.maskAsRead(idNotification);
                return ResponseEntity.ok().build();
        }

        @Operation(summary = "Get unread notifications for a user with pagination", description = "Get all notifications that have not been read by a specific user.")
        @ApiResponse(responseCode = "200", description = "List of unread notifications obtained correctly", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Notification.class))) })
        @GetMapping("/unread/{userId}")
        public ResponseEntity<List<Notification>> getUnreadNotifications(
                        @PathVariable Integer userId,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size) {

                List<Notification> unreadNotifications = notificationService.getUnreadNotifications(userId, page, size);
                return ResponseEntity.ok(unreadNotifications);
        }

        @Operation(summary = "Create notification of like", description = "Create and send a notification of like.")
        @ApiResponse(responseCode = "200", description = "Notification created correctly", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Notification.class))) })
        @PostMapping("/like")
        public ResponseEntity<String> createAndSendLikeNotification(@RequestParam int idUser) {
                User user = userService.getByIdUser(idUser);

                Notification notification = new Notification();
                notification.setIdUser(user);
                notification.setTypeNotification(TypeNotification.Like);
                notificationService.sendNotification(notification);
                return ResponseEntity.ok("Notification of like sent successfully");
        }

        @Operation(summary = "Create message notification", description = "Create and send a message notification.")
        @ApiResponse(responseCode = "200", description = "Notification created correctly", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Notification.class))) })
        @PostMapping("/message")
        public ResponseEntity<String> createAndSendMessageNotification(@RequestParam int idUser) {
                User user = userService.getByIdUser(idUser);

                Notification notification = new Notification();
                notification.setIdUser(user);
                notification.setTypeNotification(TypeNotification.Message);
                notificationService.sendNotification(notification);
                return ResponseEntity.ok("Message notification sent successfully");
        }

        @Operation(summary = "Create comment notification", description = "Create and send a comment notification.")
        @ApiResponse(responseCode = "200", description = "Notification created correctly", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Notification.class))) })
        @PostMapping("/comment")
        public ResponseEntity<String> createAndSendCommentNotification(@RequestParam int idUser) {
                User user = userService.getByIdUser(idUser);

                Notification notification = new Notification();
                notification.setIdUser(user);
                notification.setTypeNotification(TypeNotification.Comment);
                notificationService.sendNotification(notification);
                return ResponseEntity.ok("Notice of comment sent successfully");
        }

        @Operation(summary = "Create follower notification", description = "Create and send a follower notification.")
        @ApiResponse(responseCode = "200", description = "Notification created correctly", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Notification.class))) })
        @PostMapping("/follow")
        public ResponseEntity<String> createAndSendFollowNotification(@RequestParam int idUser) {
                User user = userService.getByIdUser(idUser);

                Notification notification = new Notification();
                notification.setIdUser(user);
                notification.setTypeNotification(TypeNotification.Follow);
                notificationService.sendNotification(notification);
                return ResponseEntity.ok("Follower notification sent successfully");
        }

        @Operation(summary = "Create alert notification", description = "Create and send an alert notification.")
        @ApiResponse(responseCode = "200", description = "Notification created correctly", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Notification.class))) })
        @PostMapping("/alert")
        public ResponseEntity<String> createAndSendAlertNotification(@RequestParam int idUser) {
                User user = userService.getByIdUser(idUser);

                Notification notification = new Notification();
                notification.setIdUser(user);
                notification.setTypeNotification(TypeNotification.Alert);
                notificationService.sendNotification(notification);
                return ResponseEntity.ok("Alert notification sent successfully");
        }
}