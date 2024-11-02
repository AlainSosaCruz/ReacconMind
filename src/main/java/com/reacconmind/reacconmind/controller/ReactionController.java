package com.reacconmind.reacconmind.controller;

import com.reacconmind.reacconmind.model.Reaction;
import com.reacconmind.reacconmind.model.ReactionPK;
import com.reacconmind.reacconmind.service.ReactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Reaction", description = "Operations related to reaction management.")
@RestController
@RequestMapping("/reactions")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @Operation(summary = "Retrieve all reactions with pagination")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all reactions")
    @GetMapping
    public Page<Reaction> getAllReactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reactionService.getAllReactions(pageable);
    }

    @Operation(summary = "Retrieve reactions by user ID with pagination")
    @ApiResponse(responseCode = "200", description = "Successfully found reactions for the specified user")
    @GetMapping("/user/{idUser}")
    public Page<Reaction> getReactionsByUserId(
            @PathVariable int idUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reactionService.getReactionsByUser(idUser, pageable);
    }

    @Operation(summary = "Retrieve reactions by publication ID with pagination")
    @ApiResponse(responseCode = "200", description = "Successfully found reactions for the specified publication")
    @GetMapping("/publication/{idPublication}")
    public Page<Reaction> getReactionsByPublicationId(
            @PathVariable int idPublication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reactionService.getReactionsByPublication(idPublication, pageable);
    }

    @Operation(summary = "Create a new reaction")
    @ApiResponse(responseCode = "201", description = "Reaction created successfully")
    @PostMapping
    public ResponseEntity<Reaction> createReaction(@RequestBody Reaction reaction) {
        Reaction createdReaction = reactionService.saveReaction(reaction);
        return new ResponseEntity<>(createdReaction, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing reaction")
    @ApiResponse(responseCode = "200", description = "Reaction updated successfully")
    @PutMapping
    public ResponseEntity<Reaction> updateReaction(@RequestBody Reaction reaction) {
        Reaction updatedReaction = reactionService.saveReaction(reaction);
        return new ResponseEntity<>(updatedReaction, HttpStatus.OK);
    }

    @Operation(summary = "Delete a reaction by user and publication IDs")
    @ApiResponse(responseCode = "204", description = "Reaction deleted successfully")
    @DeleteMapping("/{idUser}/{idPublication}")
    public ResponseEntity<Void> deleteReaction(@PathVariable int idUser, @PathVariable int idPublication) {
        ReactionPK reactionPK = new ReactionPK(idUser, idPublication);
        reactionService.deleteReaction(reactionPK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
