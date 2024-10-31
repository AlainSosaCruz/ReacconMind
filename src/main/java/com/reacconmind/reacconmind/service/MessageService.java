package com.reacconmind.reacconmind.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reacconmind.reacconmind.dto.MessageDTO;
import com.reacconmind.reacconmind.model.Message;
import com.reacconmind.reacconmind.model.Notification;
import com.reacconmind.reacconmind.model.NotificationStatus;
import com.reacconmind.reacconmind.model.TypeNotification;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.repository.MessageRepository;
import com.reacconmind.reacconmind.repository.NotificationStrategy;
import com.reacconmind.reacconmind.repository.UserRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationStrategy messageNotificationStrategy;

    public Message createMessage(MessageDTO messageDTO) {
        User sender = userRepository.findById(messageDTO.getSender())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User addressee = userRepository.findById(messageDTO.getAddressee())
                .orElseThrow(() -> new RuntimeException("Addressee not found"));

        Message message = new Message(sender, addressee, messageDTO.getContent(), messageDTO.getMultimedia());
        messageRepository.save(message);

        Notification notification = new Notification();
        notification.setIdUser(addressee);
        notification.setTypeNotification(TypeNotification.Message);
        notification.setState(NotificationStatus.Unread);

        messageNotificationStrategy.send(notification);
        return message;
    }

    public Message updateMessage(int id, MessageDTO messageDTO) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        message.setContent(messageDTO.getContent());
        message.setMultimedia(messageDTO.getMultimedia());
        return messageRepository.save(message);
    }

    public void deleteMessage(int id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        messageRepository.delete(message);
    }


}
