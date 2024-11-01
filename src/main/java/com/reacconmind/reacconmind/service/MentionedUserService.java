package com.reacconmind.reacconmind.service;

import com.reacconmind.reacconmind.model.MentionedUser;
import com.reacconmind.reacconmind.model.MentionedUserId;
import com.reacconmind.reacconmind.repository.MentionedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MentionedUserService {

    @Autowired
    private MentionedUserRepository repository;

    // Método para obtener todos los usuarios mencionados
    public List<MentionedUser> getAllMentionedUsers() {
        return repository.findAll();
    }

    // Método para obtener todos los usuarios mencionados con paginación
    public Page<MentionedUser> getMentionedUsers(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // Método para obtener un usuario mencionado por su ID de publicación y usuario mencionado
    public MentionedUser getMentionedUserById(Integer idPublication, Integer idMentionedUser) {
        Optional<MentionedUser> optionalMentionedUser = repository.findById(new MentionedUserId(idPublication, idMentionedUser));
        return optionalMentionedUser.orElse(null); // Retorna null si no se encuentra el usuario mencionado
    }

    // Método para guardar un nuevo usuario mencionado o actualizar uno existente
    public void saveMentionedUser(MentionedUser mentionedUser) {
        repository.save(mentionedUser);
    }

    // Método para eliminar un usuario mencionado por sus IDs
    public void deleteMentionedUser(Integer idPublication, Integer idMentionedUser) {
        repository.deleteById(new MentionedUserId(idPublication, idMentionedUser));
    }
}
