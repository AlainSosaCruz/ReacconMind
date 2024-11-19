package com.reacconmind.reacconmind.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.reacconmind.reacconmind.model.AccountUserEmail;
import com.reacconmind.reacconmind.model.GoogleAuth;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.service.AccountUserEmailService;
import com.reacconmind.reacconmind.service.GoogleAuthService;
import com.reacconmind.reacconmind.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Google Authentication", description = "Endpoints for handling Google OAuth2 authentication and user registration.")
public class GoogleAuthController {
    @Autowired
    private GoogleAuthService googleAuthService;

    @Autowired
    private UserService userService;

    public GoogleAuthController(
            UserService userService,
            GoogleAuthService googleAuthService) {
        this.userService = userService;
        this.googleAuthService = googleAuthService;
    }

    @RequestMapping("/")
    @Operation(summary = "Homepage", description = "Returns a welcome message.")
    public String home() {
        return "Welcome";
    }

    @RequestMapping("/user")
    @Operation(summary = "Get authenticated user", description = "Returns the principal of the authenticated user.")
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("/oauth2/callback/google")
    @Operation(summary = "Google OAuth2 Callback", description = "Registers a user using Google OAuth2.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully registered."),
            @ApiResponse(responseCode = "400", description = "Invalid request."),
            @ApiResponse(responseCode = "409", description = "Conflict: Google ID already registered."),
    })
    public ModelAndView googleCallback(
            @AuthenticationPrincipal OAuth2User principal) {
        String name = principal.getAttribute("name");
        String email = principal.getAttribute("email");
        String imageProfile = principal.getAttribute("picture");
        String googleId = principal.getAttribute("sub");
        String imageFacade = principal.getAttribute("picture");
        String biography = "Hi";
        String userName = principal.getAttribute("name") + "2e";

        Optional<GoogleAuth> existingUser = googleAuthService.findByGoogleId(googleId);
        User user;
        GoogleAuth accountUserEmail;
        if (existingUser.isPresent()) {
            accountUserEmail = existingUser.get();
            return new ModelAndView(
                    "redirect:http://localhost:8080/doc/swagger-ui/index.html");
        } else {
            user = new User();
            user.setName(name);
            user.setImageProfile(imageProfile);
            user.setBiography(biography);
            user.setImageFacade(imageFacade);
            user.setUserName(userName);
            userService.save(user);
        }

        Optional<GoogleAuth> existingGoogleAuth = googleAuthService.findByGoogleId(googleId);

        if (existingGoogleAuth.isPresent()) {
            return new ModelAndView(
                    "redirect:http://localhost:8080/doc/swagger-ui/index.html");
        }

        GoogleAuth googleAuth = new GoogleAuth();
        googleAuth.setUser(user);
        googleAuth.setGoogleId(googleId);
        googleAuth.setEmail(email);
        googleAuth.setUser(user);

        googleAuthService.save(googleAuth);

        return new ModelAndView(
                "redirect:http://localhost:8080/doc/swagger-ui/index.html");
    }
}
