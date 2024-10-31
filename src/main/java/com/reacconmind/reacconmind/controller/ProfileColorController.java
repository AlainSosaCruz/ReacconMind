package com.reacconmind.reacconmind.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reacconmind.reacconmind.dto.ProfileColorAddDTO;
import com.reacconmind.reacconmind.model.ProfileColor;
import com.reacconmind.reacconmind.model.ThemeType;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.service.ProfileColorService;
import com.reacconmind.reacconmind.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/profile-colors")
@Tag(name = "Profile Color", description = "Endpoints for managing user profile colors.")
public class ProfileColorController {

    @Autowired
    private ProfileColorService profileColorService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Create or update a profile color")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profile color created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<ProfileColor> createOrUpdateProfileColor(
            @Parameter(description = "Profile color data to be created") @RequestBody ProfileColorAddDTO profileColorDTO) {

        // Busca el usuario por idUser
        User user = userService.getByIdUser(profileColorDTO.getIdUser());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // o un mensaje que indique que el usuario no fue
                                                                 // encontrado
        }

        // Crea el nuevo ProfileColor
        ProfileColor profileColor = new ProfileColor();
        profileColor.setTheme(ThemeType.valueOf(profileColorDTO.getTheme())); // Asumiendo que el theme está en el
                                                                              // formato correcto
        profileColor.setUser(user);

        profileColorService.save(profileColor);
        return new ResponseEntity<>(profileColor, HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve a profile color by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile color retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Profile color not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProfileColorAddDTO> getProfileColorById(
            @Parameter(description = "ID of the profile color to retrieve", required = true) @PathVariable Integer id) {
        ProfileColor profileColor = profileColorService.findById(id);
        if (profileColor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Mapeo de ProfileColor a ProfileColorDTO
        ProfileColorAddDTO profileColorDTO = new ProfileColorAddDTO();
        profileColorDTO.setIdProfileColor(profileColor.getIdProfileColor());
        profileColorDTO.setIdUser(profileColor.getUser().getIdUser()); // Asumiendo que hay un método getIdUser en User
        profileColorDTO.setTheme(profileColor.getTheme().name()); // Si ThemeType es un enum, puedes convertirlo a
                                                                  // String

        return new ResponseEntity<>(profileColorDTO, HttpStatus.OK);
    }

    @Operation(summary = "Retrieve all profile colors for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile colors retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No profile colors found for this user")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProfileColor>> getProfileColorsByUserId(
            @Parameter(description = "User ID to retrieve profile colors", required = true) @PathVariable Integer userId) {
        List<ProfileColor> profileColors = profileColorService.findByUserId(userId);
        if (profileColors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(profileColors, HttpStatus.OK);
    }

    @Operation(summary = "Update a profile color")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile color updated successfully"),
            @ApiResponse(responseCode = "404", description = "Profile color not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProfileColorAddDTO> updateProfileColor(
            @PathVariable Integer id,
            @RequestBody ProfileColorAddDTO profileColorDTO) {
        ProfileColor existingProfileColor = profileColorService.findById(id);
        if (existingProfileColor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Actualiza los campos necesarios
        existingProfileColor.setTheme(ThemeType.valueOf(profileColorDTO.getTheme()));

        profileColorService.save(existingProfileColor);

        // Mapeo de ProfileColor a ProfileColorDTO para la respuesta
        ProfileColorAddDTO updatedProfileColorDTO = new ProfileColorAddDTO();
        updatedProfileColorDTO.setIdProfileColor(existingProfileColor.getIdProfileColor());
        updatedProfileColorDTO.setIdUser(existingProfileColor.getUser().getIdUser());
        updatedProfileColorDTO.setTheme(existingProfileColor.getTheme().name());

        return new ResponseEntity<>(updatedProfileColorDTO, HttpStatus.OK);
    }

}
