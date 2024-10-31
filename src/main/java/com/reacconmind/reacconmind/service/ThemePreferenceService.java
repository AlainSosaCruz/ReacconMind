package com.reacconmind.reacconmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reacconmind.reacconmind.dto.ThemePreferenceAddDTO;
import com.reacconmind.reacconmind.model.ThemePreference;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.repository.ThemePreferenceRepository;
import com.reacconmind.reacconmind.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ThemePreferenceService {

    @Autowired
    private ThemePreferenceRepository themePreferenceRepository;
    @Autowired
    private UserRepository userRepository;

    public ThemePreference save(ThemePreference themePreference) {
        return themePreferenceRepository.save(themePreference); // Guardar y devolver el objeto
    }

    public List<ThemePreference> findByUserId(Integer idUser) {
        return themePreferenceRepository.findPreferencesByUserId(idUser);
    }

    public List<ThemePreferenceAddDTO> getAllThemePreferences() {
        return themePreferenceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ThemePreferenceAddDTO> getPreferencesByUserId(int idUser) {
        return themePreferenceRepository.findPreferencesByUserId(idUser).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ThemePreferenceAddDTO saveThemePreference(ThemePreferenceAddDTO themePreferenceDTO) {
        ThemePreference themePreference = convertToEntity(themePreferenceDTO);
        ThemePreference savedPreference = themePreferenceRepository.save(themePreference);
        return convertToDTO(savedPreference);
    }

    public void deleteThemePreference(int id) {
        themePreferenceRepository.deleteById(id);
    }

    private ThemePreferenceAddDTO convertToDTO(ThemePreference themePreference) {
        ThemePreferenceAddDTO dto = new ThemePreferenceAddDTO();
        dto.setIdThemePreference(themePreference.getIdThemePreference());
        dto.setThemeBot(themePreference.getThemeBot());
        dto.setUserId(themePreference.getUser().getIdUser());
        return dto;
    }

    private ThemePreference convertToEntity(ThemePreferenceAddDTO dto) {
        ThemePreference themePreference = new ThemePreference();
        themePreference.setIdThemePreference(dto.getIdThemePreference());
        themePreference.setThemeBot(dto.getThemeBot());

        User user = userRepository.findById(dto.getUserId()).orElse(null);
        themePreference.setUser(user);

        return themePreference;
    }
}
