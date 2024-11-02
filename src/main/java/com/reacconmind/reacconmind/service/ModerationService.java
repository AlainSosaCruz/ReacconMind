package com.reacconmind.reacconmind.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reacconmind.reacconmind.model.Moderation;
import com.reacconmind.reacconmind.model.ModerationTypeEnum;
import com.reacconmind.reacconmind.model.Notification;
import com.reacconmind.reacconmind.model.NotificationStatus;
import com.reacconmind.reacconmind.model.TypeNotification;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.repository.ModerationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ModerationService {

    @Autowired
    private ModerationRepository moderationRepository;
    
    @Autowired
    private AzureModerationService azureModerationService;
    
    @Autowired
    private NotificationService notificationService;

    public List<Moderation> getAllModerations() {
        return moderationRepository.findAll();
    }

    public List<Moderation> getModerationsByPublication(int publicationId) {
        return moderationRepository.findByIdPublication(publicationId);
    }

    public List<Moderation> getModerationsByUser(int userId) {
        return moderationRepository.findByIdUsuario(userId);
    }

    public Optional<Moderation> getModerationById(int id) {
        return moderationRepository.findById(id);
    }
    
    public Moderation moderateText(int publicationId, String content, int userId) {
        ModerationTypeEnum decision = azureModerationService.moderateText(content);
        
        Moderation moderation = new Moderation();
        moderation.setIdPublication(publicationId);
        moderation.setIdUser(userId);
        moderation.setModerationType(decision);
        
        moderation = moderationRepository.save(moderation);

        if (decision == ModerationTypeEnum.REJECTED) {
            sendRejectionNotification(userId, publicationId);
        }
        
        return moderation;
    }

    public Moderation moderateImage(int publicationId, MultipartFile image, int userId) {
        ModerationTypeEnum decision = azureModerationService.moderateImage(image);
        
        Moderation moderation = new Moderation();
        moderation.setIdPublication(publicationId);
        moderation.setIdUser(userId);
        moderation.setModerationType(decision);
        
        moderation = moderationRepository.save(moderation);

        if (decision == ModerationTypeEnum.REJECTED) {
            sendRejectionNotification(userId, publicationId);
        }
        
        return moderation;
    }

    private void sendRejectionNotification(int userId, int publicationId) {
        Notification notification = new Notification();
        User user = new User();
        user.setIdUser(userId); // Cambiado a setIdUser basado en el modelo User proporcionado
        notification.setIdUser(user);
        notification.setTypeNotification(TypeNotification.Message);
        notification.setContent("Tu publicación #" + publicationId + " ha sido rechazada por incumplir las normas de la comunidad.");
        notification.setState(NotificationStatus.Unread);
        
        notificationService.sendNotification(notification);
    }
    public Page<Moderation> getAllModerations(Pageable pageable) {
        return moderationRepository.findAll(pageable);
    }
}