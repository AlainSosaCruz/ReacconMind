package com.reacconmind.reacconmind.controller;

import com.reacconmind.reacconmind.dto.ReactionDTO;
import com.reacconmind.reacconmind.model.Reaction;
import com.reacconmind.reacconmind.model.ReactionPK;
import com.reacconmind.reacconmind.repository.ReactionRepository;
import com.reacconmind.reacconmind.service.ReactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reaction", description = "Operations related to reaction management.")
@RestController
@RequestMapping("/reactions")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;
 

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



    @Autowired
    private ReactionRepository reactionRepository;

    @Operation(summary = "Get reactions with pagination",
            description = "Retrieve a paginated list of reactions. Specify the page number and page size to get a subset of reactions.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of reactions", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReactionDTO.class)))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid page number or page size provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("")
    public ResponseEntity<List<ReactionDTO>> getAllReactions(
            @Parameter(description = "The page number to retrieve. Default is 0 (first page).", required = false, example = "1")
            @RequestParam(value = "page", defaultValue = "0") int page,

            @Parameter(description = "The number of reactions per page. Default is 10.", required = false, example = "10")
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReactionDTO> reactionPage = reactionRepository.findAllReactions(pageable);

        // Return only the content of the ReactionDTO
        return ResponseEntity.ok(reactionPage.getContent());
    }






}
