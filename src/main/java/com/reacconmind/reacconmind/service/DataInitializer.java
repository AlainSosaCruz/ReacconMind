package com.reacconmind.reacconmind.service;

import com.reacconmind.reacconmind.model.AccountUserEmail;
import com.reacconmind.reacconmind.model.ProfileColor;
import com.reacconmind.reacconmind.model.StatusType;
import com.reacconmind.reacconmind.model.ThemeBotType;
import com.reacconmind.reacconmind.model.ThemePreference;
import com.reacconmind.reacconmind.model.ThemeType;
import com.reacconmind.reacconmind.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountUserEmailService accountUserEmailService;

    @Autowired
    private ProfileColorService profileColorService;

    @Autowired
    ThemePreferenceService themePreferenceService;

    public DataInitializer(UserService userService, AccountUserEmailService accountUserEmailService) {
        this.userService = userService;
        this.accountUserEmailService = accountUserEmailService;
    }

    @Override
    public void run(String... args) throws Exception {

        createUserIfNotExists("User One", "https://example.com/image1.jpg",
                "https://example.com/facade1.jpg", "Biography of User One",
                "userone", "https://example.com/thumbnail1.jpg",
                "user1@example.com", "aascsosa12");

        createUserIfNotExists("User Two", "https://example.com/image2.jpg",
                "https://example.com/facade2.jpg", "Biography of User Two",
                "usertwo", "https://example.com/thumbnail2.jpg",
                "user2@example.com", "hashedpassword2");

        createUserIfNotExists("User Three", "https://example.com/image3.jpg",
                "https://example.com/facade3.jpg", "Biography of User Three",
                "userthree", "https://example.com/thumbnail3.jpg",
                "user3@example.com", "hashedpassword3");
    }

    private void createUserIfNotExists(String name, String imageProfile, String imageFacade,
            String biography, String userName, String thumbnail, String email, String password) {

        // Verificar si el usuario ya existe
        if (userService.findByUserName(userName) == null) {
            User user = new User();
            user.setName(name);
            user.setImageProfile(imageProfile);
            user.setImageFacade(imageFacade);
            user.setBiography(biography);
            user.setUserName(userName);
            user.setThumbnail(thumbnail);
            user.setStatus(StatusType.Active);

            userService.save(user);

            // Verificar si el correo electrónico ya existe
            if (accountUserEmailService.findByEmail(email) == null) {
                // Crear y guardar el correo electrónico del usuario
                accountUserEmailService.save(new AccountUserEmail(email, password, user));
                profileColorService.save(new ProfileColor(ThemeType.Dark, user));
                themePreferenceService.save(new ThemePreference(ThemeBotType.Movies, user));
                themePreferenceService.save(new ThemePreference(ThemeBotType.Music, user));
            } else {
                System.out.println("El correo electrónico '" + email + "' ya existe.");
            }
        } else {
            System.out.println("El usuario con el nombre de usuario '" + userName + "' ya existe.");
        }
    }
}
