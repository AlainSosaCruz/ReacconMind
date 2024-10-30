package com.reacconmind.reacconmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reacconmind.reacconmind.model.ProfileColor;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.repository.ProfileColorRepository;
import java.util.List;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProfileColorService {

    @Autowired
    private ProfileColorRepository profileColorRepository;

    public void save(ProfileColor profileColor) {
        profileColorRepository.save(profileColor);
    }

    public ProfileColor findById(Integer id) {
        return profileColorRepository.findById(id).orElse(null);
    }

    public List<ProfileColor> findByUserId(Integer userId) {
        return profileColorRepository.findProfileColorByUserId(userId);
    }

    public ProfileColor findByUser(User user) {
        return profileColorRepository.findByUser(user);
    }
}
