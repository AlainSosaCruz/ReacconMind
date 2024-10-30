package com.reacconmind.reacconmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reacconmind.reacconmind.model.ThemePreference;
import com.reacconmind.reacconmind.repository.ThemePreferenceRepository;
import java.util.List;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ThemePreferenceService {

    @Autowired
    private ThemePreferenceRepository themePreferenceRepository;

    public ThemePreference save(ThemePreference themePreference) {
        return themePreferenceRepository.save(themePreference); // Guardar y devolver el objeto
    }

    public List<ThemePreference> findByUserId(Integer idUser) {
        return themePreferenceRepository.findPreferencesByUserId(idUser);
    }
}
