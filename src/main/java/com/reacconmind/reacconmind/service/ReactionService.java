package com.reacconmind.reacconmind.service;

import com.reacconmind.reacconmind.model.Reaction;
import com.reacconmind.reacconmind.model.ReactionPK;
import com.reacconmind.reacconmind.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    // Método para guardar o actualizar una reacción
    public Reaction saveReaction(Reaction reaction) {
        return reactionRepository.save(reaction);
    }

    // Método para obtener todas las reacciones
    public List<Reaction> getAllReactions() {
        return reactionRepository.findAll();
    }

    // Método para obtener reacciones por usuario
    public List<Reaction> getReactionsByUser(int idUser) {
        return reactionRepository.findById_IdUser(idUser);
    }

    // Método para obtener reacciones por publicación
    public List<Reaction> getReactionsByPublication(int idPublication) {
        return reactionRepository.findById_IdPublication(idPublication);
    }

    // Método para eliminar una reacción por su clave primaria compuesta
    public void deleteReaction(ReactionPK reactionPK) {
        reactionRepository.deleteById(reactionPK);
    }
}
