package com.reacconmind.reacconmind.controller;

import com.reacconmind.reacconmind.dto.ReplyDTO;
import com.reacconmind.reacconmind.model.Comment;
import com.reacconmind.reacconmind.model.Reply;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.repository.CommentRepository;
import com.reacconmind.reacconmind.repository.ReplyRepository;
import com.reacconmind.reacconmind.repository.UserRepository;
import com.reacconmind.reacconmind.service.ReplyService;
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

@Tag(name = "Reply", description = "Operations related to reply management.")
@RestController
@RequestMapping("/replies")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
public class ReplyController {

    @Autowired
    private ReplyService service;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;



    @Operation(summary = "Get a reply by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reply found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Reply.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid reply ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reply not found", content = @Content) })
    @GetMapping("/replies/{idReply}")
    public ResponseEntity<?> getReplyById(@PathVariable int idReply) {
        // Buscar la respuesta en la base de datos
        Optional<Reply> replyOptional = replyRepository.findById(idReply);

        if (replyOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reply not found");
        }

        // Obtener la respuesta
        Reply reply = replyOptional.get();

        // Crear el DTO con los datos de la respuesta
        ReplyDTO responseDTO = new ReplyDTO(
                reply.getIdReply(),
                reply.getUser().getIdUser(),
                reply.getComment().getIdComment(),
                reply.getContentReply()
        );

        // Devolver el DTO como respuesta
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Create a new reply")
    @ApiResponse(responseCode = "200", description = "Reply created", content = @Content)
    @PostMapping("/replies")
    public ResponseEntity<?> createReply(@RequestBody ReplyDTO replyDTO) {
        // Busca el usuario y el comentario usando los IDs recibidos
        Optional<User> userOptional = userRepository.findById(replyDTO.getIdUser());
        Optional<Comment> commentOptional = commentRepository.findById(replyDTO.getIdComment());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (commentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
        }

        // Crea la nueva respuesta
        User user = userOptional.get();
        Comment comment = commentOptional.get();
        Reply reply = new Reply();
        reply.setUser(user);
        reply.setComment(comment);
        reply.setContentReply(replyDTO.getContentReply());

        // Guarda la respuesta en la base de datos
        replyRepository.save(reply);

        // Crear el DTO con solo los campos necesarios
        ReplyDTO responseDTO = new ReplyDTO(
                reply.getIdReply(),
                reply.getUser().getIdUser(),
                reply.getComment().getIdComment(),
                reply.getContentReply()
        );

        // Devolver el DTO con la estructura deseada
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Update a reply")
    @ApiResponse(responseCode = "200", description = "Reply updated", content = @Content)
    @PutMapping("/replies/{idReply}")
    public ResponseEntity<?> updateReply(@PathVariable int idReply, @RequestBody ReplyDTO replyDTO) {
        // Busca la respuesta a actualizar
        Optional<Reply> replyOptional = replyRepository.findById(idReply);

        if (replyOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reply not found");
        }

        // Busca el usuario y el comentario usando los IDs recibidos en el DTO
        Optional<User> userOptional = userRepository.findById(replyDTO.getIdUser());
        Optional<Comment> commentOptional = commentRepository.findById(replyDTO.getIdComment());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (commentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
        }

        // Actualiza los datos de la respuesta
        Reply reply = replyOptional.get();
        User user = userOptional.get();
        Comment comment = commentOptional.get();

        // Actualiza solo los campos necesarios
        reply.setUser(user);
        reply.setComment(comment);
        reply.setContentReply(replyDTO.getContentReply());

        // Guarda la respuesta actualizada
        replyRepository.save(reply);

        // Crear el DTO con los datos actualizados
        ReplyDTO responseDTO = new ReplyDTO(
                reply.getIdReply(),
                reply.getUser().getIdUser(),
                reply.getComment().getIdComment(),
                reply.getContentReply()
        );

        // Devolver el DTO actualizado
        return ResponseEntity.ok(responseDTO);
    }


    @Operation(summary = "Delete a reply by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reply deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reply not found", content = @Content) })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        service.deleteReply(id);
        return new ResponseEntity<>("Reply deleted", HttpStatus.OK);
    }


    @Operation(summary = "Get replies with pagination", description = "Retrieve a paginated list of replies. Specify the page number and page size to get a subset of replies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of replies", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReplyDTO.class)))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid page number or page size provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("")
    public ResponseEntity<List<ReplyDTO>> getAllReplies(
            @Parameter(description = "The page number to retrieve. Default is 0 (first page).", required = false, example = "1")
            @RequestParam(value = "page", defaultValue = "0") int page,

            @Parameter(description = "The number of replies per page. Default is 10.", required = false, example = "10")
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReplyDTO> replyPage = replyRepository.findAllReplies(pageable);

        // Retornar solo el contenido de los ReplyDTO
        return ResponseEntity.ok(replyPage.getContent());

}
}
