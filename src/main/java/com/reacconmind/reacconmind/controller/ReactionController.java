package com.reacconmind.reacconmind.controller;

import com.reacconmind.reacconmind.model.Reaction;
import com.reacconmind.reacconmind.model.ReactionPK;
import com.reacconmind.reacconmind.service.ReactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reactions")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @Operation(summary = "Get all reactions")
    @ApiResponse(responseCode = "200", description = "Found Reactions")
    @GetMapping
    public List<Reaction> getAllReactions() {
        return reactionService.getAllReactions();
    }

    @Operation(summary = "Get a reactions by its ID")
    @ApiResponse(responseCode = "200", description = "Reactions found")
    @GetMapping("/user/{idUser}")
    public List<Reaction> getReactionsByUserId(@PathVariable int idUser) {
        return reactionService.getReactionsByUser(idUser);
    }

    @Operation(summary = "Obtener reacciones por ID de publicación")
    @ApiResponse(responseCode = "200", description = "Reacciones encontradas para la publicación")
    @GetMapping("/publication/{idPublication}")
    public List<Reaction> getReactionsByPublicationId(@PathVariable int idPublication) {
        return reactionService.getReactionsByPublication(idPublication);
    }

    @Operation(summary = "Crear una nueva reacción")
    @ApiResponse(responseCode = "201", description = "Reacción creada exitosamente")
    @PostMapping
    public ResponseEntity<Reaction> createReaction(@RequestBody Reaction reaction) {
        Reaction createdReaction = reactionService.saveReaction(reaction);
        return new ResponseEntity<>(createdReaction, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar una reacción")
    @ApiResponse(responseCode = "200", description = "Reacción actualizada exitosamente")
    @PutMapping
    public ResponseEntity<Reaction> updateReaction(@RequestBody Reaction reaction) {
        Reaction updatedReaction = reactionService.saveReaction(reaction);
        return new ResponseEntity<>(updatedReaction, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar una reacción")
    @ApiResponse(responseCode = "204", description = "Reacción eliminada exitosamente")
    @DeleteMapping("/{idUser}/{idPublication}")
    public ResponseEntity<Void> deleteReaction(@PathVariable int idUser, @PathVariable int idPublication) {
        ReactionPK reactionPK = new ReactionPK(idUser, idPublication);
        reactionService.deleteReaction(reactionPK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
