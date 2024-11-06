package com.reacconmind.reacconmind.controller;

import com.reacconmind.reacconmind.dto.CommentDTO;
import com.reacconmind.reacconmind.model.Bot;
import com.reacconmind.reacconmind.model.Comment;
import com.reacconmind.reacconmind.model.Publication;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.repository.BotRepository;
import com.reacconmind.reacconmind.repository.CommentRepository;
import com.reacconmind.reacconmind.repository.PublicationRepository;
import com.reacconmind.reacconmind.repository.UserRepository;
import com.reacconmind.reacconmind.service.CommentService;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Comment", description = "Operations related to comments in the application.")
@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
public class CommentController {

    @Autowired
    private CommentService service;

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    @Operation(summary = "Get a comment by its ID")
    @ApiResponse(responseCode = "200", description = "Comment found", content = @Content)
    @GetMapping("/comments/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable int id) {
        try {
            // Busca el comentario por su ID
            Optional<Comment> commentOptional = commentRepository.findById(id);

            // Verifica si el comentario existe
            if (commentOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
            }

            // Obtiene el comentario encontrado
            Comment comment = commentOptional.get();

            // Convierte la entidad Comment a CommentDTO
            CommentDTO responseDTO = new CommentDTO(
                    comment.getIdComment(),
                    comment.getUser().getIdUser(),
                    comment.getPublication().getIdPublication(),
                    comment.getContentComment()
            );

            // Devuelve la respuesta con el DTO
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            // Captura cualquier error y responde con un mensaje claro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request: " + e.getMessage());
        }
    }



    @Operation(summary = "Create a new comment")
    @ApiResponse(responseCode = "200", description = "Comment created", content = @Content)
    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentDTO commentDTO) {

        Optional<User> userOptional = userRepository.findById(commentDTO.getIdUser());
        Optional<Publication> publicationOptional = publicationRepository.findById(commentDTO.getIdPublication());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (publicationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publication not found");
        }

        User user = userOptional.get();
        Publication publication = publicationOptional.get();
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPublication(publication);
        comment.setContentComment(commentDTO.getContentComment());

        commentRepository.save(comment);

        CommentDTO responseDTO = new CommentDTO(comment.getIdComment(),
                comment.getUser().getIdUser(),
                comment.getPublication().getIdPublication(),
                comment.getContentComment());
        return ResponseEntity.ok(responseDTO);
    }


    @Operation(summary = "Update an existing comment")
    @ApiResponse(responseCode = "200", description = "Comment updated successfully", content = @Content)
    @PutMapping("/comments/{id}")
    public ResponseEntity<?> updateComment(@PathVariable int id, @RequestBody CommentDTO commentDTO) {
        // Busca el comentario a actualizar
        Optional<Comment> commentOptional = commentRepository.findById(id);

        // Verifica si el comentario existe
        if (commentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
        }

        // Busca el usuario y la publicación usando los IDs recibidos
        Optional<User> userOptional = userRepository.findById(commentDTO.getIdUser());
        Optional<Publication> publicationOptional = publicationRepository.findById(commentDTO.getIdPublication());

        // Verifica si el usuario existe
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Verifica si la publicación existe
        if (publicationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publication not found");
        }

        // Obtiene el comentario, usuario y publicación
        Comment comment = commentOptional.get();
        User user = userOptional.get();
        Publication publication = publicationOptional.get();

        // Actualiza los campos del comentario
        comment.setUser(user);
        comment.setPublication(publication);
        comment.setContentComment(commentDTO.getContentComment());

        // Guarda el comentario actualizado en la base de datos
        commentRepository.save(comment);

        // Convierte la entidad Comment a CommentDTO para la respuesta
        CommentDTO responseDTO = new CommentDTO(
                comment.getIdComment(),
                comment.getUser().getIdUser(),
                comment.getPublication().getIdPublication(),
                comment.getContentComment()
        );

        // Responde con el DTO del comentario actualizado
        return ResponseEntity.ok(responseDTO);
    }


    @Operation(summary = "Delete a comment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment deleted"),
            @ApiResponse(responseCode = "404", description = "Comment not found")})
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        Comment existingComment = service.getCommentById(id);
        if (existingComment != null) {
            service.deleteComment(id);
            return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Get comments with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found comments", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentDTO.class)))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid page number or size"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("")
    public ResponseEntity<List<CommentDTO>> findAllComments(
            @Parameter(description = "The page number to retrieve. Default is 0.", required = false, example = "0")
            @RequestParam(value = "page", defaultValue = "0") int page,

            @Parameter(description = "The number of comments per page. Default is 10.", required = false, example = "10")
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CommentDTO> commentPage = commentRepository.findAllComments(pageable);

        // Retornar solo el contenido de los comentarios
        return ResponseEntity.ok(commentPage.getContent());
    }
}
