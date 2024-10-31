package com.reacconmind.reacconmind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.reacconmind.reacconmind.dto.ThemePreferenceAddDTO;
import com.reacconmind.reacconmind.service.ThemePreferenceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Theme Preferences", description = "Endpoints for managing theme preferences for users.")
@RequestMapping("/theme-preferences")
public class ThemePreferenceController {

    @Autowired
    private ThemePreferenceService themePreferenceService;

    @Operation(summary = "Get theme preferences by user ID")
    @ApiResponse(responseCode = "200", description = "Theme preferences found for user")
    @GetMapping("/user/{idUser}")
    public List<ThemePreferenceAddDTO> getThemePreferencesByUserId(@PathVariable int idUser) {
        return themePreferenceService.getPreferencesByUserId(idUser);
    }

    @Operation(summary = "Create a new theme preference")
    @ApiResponse(responseCode = "201", description = "Theme preference created successfully")
    @PostMapping
    public ResponseEntity<ThemePreferenceAddDTO> createThemePreference(
            @RequestBody ThemePreferenceAddDTO themePreferenceDTO) {
        ThemePreferenceAddDTO createdPreference = themePreferenceService.saveThemePreference(themePreferenceDTO);
        return new ResponseEntity<>(createdPreference, HttpStatus.CREATED);
    }

}
