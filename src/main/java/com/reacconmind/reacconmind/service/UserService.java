package com.reacconmind.reacconmind.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reacconmind.reacconmind.dto.AccountUserEmailAddDTO;
import com.reacconmind.reacconmind.dto.UserAddDTO;
import com.reacconmind.reacconmind.model.AccountUserEmail;
import com.reacconmind.reacconmind.model.StatusType;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    FirebaseUser firebaseUser;

    @Autowired
    private AccountUserEmailService accountUserEmailService;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public boolean userExists(Integer idUser, String email, String userName) {
        return (userRepository.existsById(idUser) ||
                userRepository.findByUserName(userName) != null);
    }

    public List<User> getAllActive(int page, int pageSize) {
        PageRequest pageRequest= PageRequest.of(page, pageSize);
        return userRepository
                .findAll()
                .stream()
                .filter(user -> user.getStatus() == StatusType.Active)
                .collect(Collectors.toList());
    }

    public void save(User user) {
        userRepository.save(user);

    }
      public void saveUser(UserAddDTO userDTO) {
        User user = convertFromDTO(userDTO);
        AccountUserEmail add = new AccountUserEmail();
        add.setEmail(userDTO.getEmail());
        add.setPassword(userDTO.getPassword());
        add.setIdUser(user);
        accountUserEmailService.save(add);
        userRepository.save(user);

    }

    public void updateUser(UserAddDTO userDTO, Integer idUser) {
        // Buscar usuario existente
        User existingUser = getByIdUser(idUser);

        // Actualizar campos
        existingUser.setName(userDTO.getName());
        existingUser.setBiography(userDTO.getBiography());
        existingUser.setUserName(userDTO.getUserName());
        existingUser.setImageProfile(userDTO.getImageProfile());
        existingUser.setImageFacade(userDTO.getImageFacade());

        // Guardar cambios
        userRepository.save(existingUser);
    }

    public User saveUsser(UserAddDTO userDTO) {
        User user = convertFromDTO(userDTO);

        return userRepository.save(user);

    }

    public UserAddDTO saveUsers(UserAddDTO userDTO) {
        User user = convertFromDTO(userDTO);
        userRepository.save(user);
        return userDTO;

    }

    public User getByIdUser(Integer idUser) {
        return userRepository.findById(idUser).get();
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<User> getAll(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<User> users = userRepository.findAll(pageRequest);
        return users.getContent();
    }

    public String uploadImageAndUpdateUser(MultipartFile multipartFile, Integer userId) {
        if (multipartFile.isEmpty()) {
            return "The file is empty.";
        }

        FirebaseUser.UploadResponse uploadResponse = firebaseUser.upload(multipartFile);
        if (uploadResponse == null) {
            return "Error uploading the image.";
        }

        User user = getByIdUser(userId);
        if (user == null) {
            return "User not found.";
        }

        user.setImageProfile(uploadResponse.getOriginalUrl());
        user.setThumbnail(uploadResponse.getThumbnailUrl());

        save(user);

        return "Image updated successfully.";
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private UserAddDTO convertToDTO(User user) {
        UserAddDTO userDTO = new UserAddDTO();
        userDTO.setIdUser(user.getIdUser());
        userDTO.setName(user.getName());
        userDTO.setImageProfile(user.getImageProfile());
        userDTO.setBiography(user.getBiography());
        userDTO.setImageFacade(user.getImageFacade());
        userDTO.setUserName(user.getUserName());
        return userDTO;
    }

    private User convertFromDTO(UserAddDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setBiography(userDTO.getBiography());
        user.setUserName(userDTO.getUserName());
        return user;
    }

}
