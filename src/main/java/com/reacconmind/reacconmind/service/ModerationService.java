package com.reacconmind.reacconmind.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reacconmind.reacconmind.model.Moderation;
import com.reacconmind.reacconmind.model.ModerationType;
import com.reacconmind.reacconmind.model.Notification;
import com.reacconmind.reacconmind.model.NotificationStatus;
import com.reacconmind.reacconmind.model.Publication;
import com.reacconmind.reacconmind.model.TypeNotification;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.model.ModerationPK;
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

    public List<Moderation> getModerationsByPublication(Publication publication) {
        return moderationRepository.findByIdPublication(publication);
    }

    public List<Moderation> getModerationsByUser(User user) {
        return moderationRepository.findByIdUser(user);
    }

    public Optional<Moderation> getModerationById(ModerationPK id) {
        return moderationRepository.findById(id);
    }
    
    public Moderation moderateText(int publicationId, String content, int userId) {
        ModerationType decision = azureModerationService.moderateText(content);
        
        Moderation moderation = new Moderation();
        ModerationPK moderationPK = new ModerationPK();
        
        moderationPK.setIdUser(userId);
        moderationPK.setIdPublication(publicationId);
        moderationPK.setIdModerationType(decision.name());
        
        moderation.setId(moderationPK);
        moderation.setModerationType(decision);
        moderation = moderationRepository.save(moderation);

        if (decision == ModerationType.REJECTED) {
            sendRejectionNotification(userId, publicationId);
        }
        
        return moderation;
    }

    public Moderation moderateImage(int publicationId, MultipartFile image, int userId) {
        ModerationType decision = azureModerationService.moderateImage(image);
        
        Moderation moderation = new Moderation();
        ModerationPK moderationPK = new ModerationPK();
        
        moderationPK.setIdUser(userId);
        moderationPK.setIdPublication(publicationId);
        moderationPK.setIdModerationType(decision.name());
        
        moderation.setId(moderationPK);
        moderation.setModerationType(decision);
        moderation = moderationRepository.save(moderation);

        if (decision == ModerationType.REJECTED) {
            sendRejectionNotification(userId, publicationId);
        }
        
        return moderation;
    }

    private void sendRejectionNotification(int userId, int publicationId) {
        Notification notification = new Notification();
        User user = new User();
        user.setIdUser(userId);
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