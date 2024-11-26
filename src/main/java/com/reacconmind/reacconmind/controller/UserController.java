package com.reacconmind.reacconmind.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.reacconmind.reacconmind.dto.UserAddDTO;
import com.reacconmind.reacconmind.dto.UserDTO;
import com.reacconmind.reacconmind.model.StatusType;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.service.UserService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200", methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.DELETE,
                RequestMethod.PUT,
})
@Configuration
@Tag(name = "User Management", description = "Manage users in the ReacconMind application, including adding, updating, and retrieving users.")
@OpenAPIDefinition(info = @Info(title = "ReacconMind API", description = "API for user management in ReacconMind", version = "1.0"))
public class UserController {

        @Autowired
        private UserService userService;

        @Autowired
        private ModelMapper modelMapper;

        @Operation(summary = "Get all Users with pagination", description = "Retrieve a paginated list of users.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved users", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))
                        }),
                        @ApiResponse(responseCode = "400", description = "Invalid pagination parameters"),
                        @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @GetMapping(value = "pagination", params = { "page", "pageSize" })
        public List<UserDTO> getAllPaginated(
                        @Parameter(description = "Page number to retrieve", example = "1") @RequestParam(value = "page", defaultValue = "0") int page,
                        @Parameter(description = "Number of users per page", example = "10") @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

                List<User> users = userService.getAll(page, pageSize);
                return users.stream()
                                .map(this::convertUserToDto)
                                .collect(Collectors.toList());
        }

        @Operation(summary = "Get all active Users", description = "Retrieve a list of active users.")
        @ApiResponse(responseCode = "200", description = "Successfully retrieved active users", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))
        })
        @GetMapping("/usersActive")
        public List<UserDTO> getAllUserActive() {
                List<User> usersActive = userService.getAllActive();
                return usersActive.stream()
                                .map(this::convertUserToDto)
                                .collect(Collectors.toList());
        }

        @Operation(summary = "Get user by ID", description = "Retrieve user details by ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved user", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                        }),
                        @ApiResponse(responseCode = "404", description = "User not found")
        })
        @GetMapping("/{idUser}")
        public ResponseEntity<?> getByIdUser(@PathVariable Integer idUser) {
                User user = userService.getByIdUser(idUser);
                return ResponseEntity.ok(user);
        }

        @Operation(summary = "Add a new User", description = "Create a new user in the system.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User successfully created"),
                        @ApiResponse(responseCode = "400", description = "Invalid input data")
        })
        @PreAuthorize("permitAll()")
        @PostMapping
        public ResponseEntity<String> addUser(@Valid @RequestBody UserAddDTO user) {
                userService.saveUser(user);
                return ResponseEntity.ok("User added successfully");
        }

        @Operation(summary = "Update an existing User", description = "Update the data of an existing user based on their ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Update successfully", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)),
                        }),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content),
        })
        @PutMapping("/update/{idUser}")
        public ResponseEntity<?> update(
                        @RequestBody UserAddDTO user,
                        @PathVariable Integer idUser) {
                try {
                        userService.updateUser(user, idUser);
                        return ResponseEntity.ok().build(); // Solo devuelve un código 200 OK sin contenido
                } catch (NoSuchElementException e) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body("Error al actualizar el perfil.");
                }
        }

        @Operation(summary = "Update user status", description = "Update the status of an existing user based on their ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User status updated successfully", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)),
                        }),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Invalid status data", content = @Content),
        })
        @PutMapping("/updateStatus/{idUser}")
        public ResponseEntity<?> updateUserStatus(
                        @RequestBody StatusType status,
                        @PathVariable Integer idUser) {
                User user = userService.getByIdUser(idUser);
                if (user == null) {
                        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
                }
                user.setStatus(status);
                userService.save(user);
                return new ResponseEntity<>(
                                "User status updated successfully",
                                HttpStatus.OK);
        }

        /*
         * @PostMapping(value = "/upload-image", consumes = { "multipart/form-data" })
         * public String upload(@RequestParam("multipartFile") MultipartFile
         * multipartFile) {
         * return firebaseUser.upload(multipartFile);
         * }
         */
        @PutMapping(value = "/upload-image/{idUser}", consumes = { "multipart/form-data" })
        @Operation(summary = "Upload Profile Image", description = "This endpoint allows uploading a profile image for the user specified by ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Image updated successfully."),
                        @ApiResponse(responseCode = "400", description = "The file is empty or error uploading the image."),
                        @ApiResponse(responseCode = "404", description = "User not found.")
        })
        public ResponseEntity<String> upload(
                        @Parameter(description = "The image file to upload", required = true) @RequestParam("multipartFile") MultipartFile multipartFile,
                        @Parameter(description = "The ID of the user whose image will be updated", required = true) @PathVariable("idUser") Integer userId) {

                String responseMessage = userService.uploadImageAndUpdateUser(multipartFile, userId);

                if (responseMessage.equals("The file is empty.") ||
                                responseMessage.equals("Error uploading the image.")) {
                        return ResponseEntity.badRequest().body(responseMessage);
                } else if (responseMessage.equals("User not found.")) {
                        return ResponseEntity.notFound().build();
                } else {
                        return ResponseEntity.ok(responseMessage);
                }
        }

        @GetMapping("/user/email/{email}")
        public User getUserByEmail(@PathVariable String email) {
                return userService.findUserByEmail(email);
        }

        private UserDTO convertUserToDto(User user) {
                UserDTO userDTO = modelMapper.map(user, UserDTO.class);

                // Asignar el email dependiendo de la autenticación
                if (user.getAccountUserGoogle() != null) {
                        userDTO.setEmail(user.getAccountUserGoogle().getEmail());
                } else if (user.getAccountUserEmail() != null) {
                        userDTO.setEmail(user.getAccountUserEmail().getEmail());
                }

                return userDTO;
        }
}
