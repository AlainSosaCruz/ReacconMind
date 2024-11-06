package com.reacconmind.reacconmind.controller;

import com.reacconmind.reacconmind.dto.ReactionDTO;
import com.reacconmind.reacconmind.model.Publication;
import com.reacconmind.reacconmind.model.Reaction;
import com.reacconmind.reacconmind.model.ReactionPK;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.repository.PublicationRepository;
import com.reacconmind.reacconmind.repository.ReactionRepository;
import com.reacconmind.reacconmind.repository.UserRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Reaction", description = "Operations related to reaction management.")
@RestController
@RequestMapping("/reactions")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PublicationRepository publicationRepository;
 

//    @Operation(summary = "Retrieve reactions by user ID with pagination")
//    @ApiResponse(responseCode = "200", description = "Successfully found reactions for the specified user")
//    @GetMapping("/user/{idUser}")
//    public Page<Reaction> getReactionsByUserId(
//            @PathVariable int idUser,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return reactionService.getReactionsByUser(idUser, pageable);
//    }

    @Operation(summary = "Retrieve reactions by publication ID with pagination")
    @ApiResponse(responseCode = "200", description = "Successfully found reactions for the specified publication")
    @GetMapping("/publication/{idPublication}")
    public ResponseEntity<List<ReactionDTO>> getReactionsByPublicationId(
            @PathVariable int idPublication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Obtener las reacciones de la publicación
        Page<Reaction> reactionsPage = reactionService.getReactionsByPublication(idPublication, pageable);

        // Convertir la Page<Reaction> a una lista de ReactionDTO
        List<ReactionDTO> reactionsDTOList = reactionsPage.stream()
                .map(reaction -> new ReactionDTO(
                        reaction.getId().getIdUser(),
                        reaction.getId().getIdPublication(),
                        reaction.getLiked()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(reactionsDTOList);
    }


    @Operation(summary = "Create a new reaction")
    @ApiResponse(responseCode = "200", description = "Reaction created", content = @Content)
    @PostMapping("/reactions")
    public ResponseEntity<?> createReaction(@RequestBody ReactionDTO reactionDTO) {
        // Busca el usuario y la publicación usando los IDs recibidos
        Optional<User> userOptional = userRepository.findById(reactionDTO.getIdUser());
        Optional<Publication> publicationOptional = publicationRepository.findById(reactionDTO.getIdPublication());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (publicationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publication not found");
        }

        // Crea la nueva reacción
        User user = userOptional.get();
        Publication publication = publicationOptional.get();
        Reaction reaction = new Reaction();

        // Crear el composite key ReactionPK
        ReactionPK reactionPK = new ReactionPK(reactionDTO.getIdUser(), reactionDTO.getIdPublication());
        reaction.setId(reactionPK);  // Set the composite key

        // Set the User and Publication entities
        reaction.setUser(user);
        reaction.setPublication(publication);

        // Set the 'liked' status
        reaction.setLiked(reactionDTO.isLiked());

        // Guarda la reacción en la base de datos
        reactionRepository.save(reaction);

        // Crear el DTO con solo los campos necesarios
        ReactionDTO responseDTO = new ReactionDTO(
                reaction.getId().getIdUser(),  // ID del usuario que reaccionó
                reaction.getId().getIdPublication(),  // ID de la publicación
                reaction.getLiked()  // Estado de la reacción (like/dislike)
        );

        // Devolver el DTO con la estructura deseada
        return ResponseEntity.ok(responseDTO);
    }



    @Operation(summary = "Update an existing reaction")
    @ApiResponse(responseCode = "200", description = "Reaction updated", content = @Content)
    @PutMapping("/reactions")
    public ResponseEntity<?> updateReaction(@RequestBody ReactionDTO reactionDTO) {
        // Busca la reacción existente usando los IDs recibidos
        Optional<Reaction> reactionOptional = reactionRepository.findById(new ReactionPK(reactionDTO.getIdUser(), reactionDTO.getIdPublication()));

        if (reactionOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reaction not found");
        }

        // Busca el usuario y la publicación usando los IDs recibidos
        Optional<User> userOptional = userRepository.findById(reactionDTO.getIdUser());
        Optional<Publication> publicationOptional = publicationRepository.findById(reactionDTO.getIdPublication());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (publicationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publication not found");
        }

        // Obtiene la reacción existente
        Reaction reaction = reactionOptional.get();
        User user = userOptional.get();
        Publication publication = publicationOptional.get();

        // Actualiza el estado de la reacción
        reaction.setUser(user);
        reaction.setPublication(publication);
        reaction.setLiked(reactionDTO.isLiked());

        // Guarda la reacción actualizada en la base de datos
        reactionRepository.save(reaction);

        // Crear el DTO con solo los campos necesarios
        ReactionDTO responseDTO = new ReactionDTO(
                reaction.getId().getIdUser(),  // ID del usuario que reaccionó
                reaction.getId().getIdPublication(),  // ID de la publicación
                reaction.getLiked()  // Estado de la reacción (like/dislike)
        );

        // Devolver el DTO con la estructura deseada
        return ResponseEntity.ok(responseDTO);
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
