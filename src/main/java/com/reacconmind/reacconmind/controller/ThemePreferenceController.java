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

import com.reacconmind.reacconmind.model.ThemeBotType;
import com.reacconmind.reacconmind.model.ThemePreference;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.service.ThemePreferenceService;
import com.reacconmind.reacconmind.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/theme-preferences")
public class ThemePreferenceController {

    @Autowired
    private ThemePreferenceService themePreferenceService;

    @Autowired
    private UserService userService; 

    @Operation(summary = "Retrieve theme preferences by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Theme preferences retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No preferences found for the user")
    })
    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<ThemePreference>> getPreferencesByUserId(
            @Parameter(description = "ID of the user to retrieve theme preferences", required = true) @PathVariable Integer idUser) {
        List<ThemePreference> preferences = themePreferenceService.findByUserId(idUser);
        if (preferences.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(preferences);
        }
    }

    @Operation(summary = "Create a new theme preference for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Theme preference created successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/user/{idUser}")
    public ResponseEntity<ThemePreference> createThemePreference(
            @Parameter(description = "ID of the user to create theme preference for", required = true) @PathVariable Integer idUser) {
        User user = userService.getByIdUser(idUser); // Asegúrate de que este método existe en tu UserService
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Crear una nueva preferencia de tema utilizando el tipo de tema predeterminado
        ThemePreference newPreference = new ThemePreference();
        newPreference.setUser(user); // Establecer el usuario
        newPreference.setThemeBot(ThemeBotType.CombinatedMedia); // Establecer un tipo de tema predeterminado

        // Guardar la nueva preferencia
        ThemePreference savedPreference = themePreferenceService.save(newPreference);

        return new ResponseEntity<>(savedPreference, HttpStatus.CREATED);
    }
}
