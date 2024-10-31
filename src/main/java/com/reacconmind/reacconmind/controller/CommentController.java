package com.reacconmind.reacconmind.controller;

import com.reacconmind.reacconmind.dto.CommentDTO;
import com.reacconmind.reacconmind.model.Comment;
import com.reacconmind.reacconmind.repository.CommentRepository;
import com.reacconmind.reacconmind.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment")
@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
public class CommentController {

    @Autowired
    private CommentService service;

    @Autowired
    private CommentRepository commentRepository;

    @Operation(summary = "Get all comments")
    @ApiResponse(responseCode = "200", description = "Found Comments", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comment.class)))})
    @GetMapping
    public List<Comment> getAll() {
        return service.getAllComments();
    }

    @Operation(summary = "Get a comment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid comment ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)})
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Comment comment = service.getCommentById(id);
        return comment != null
                ? new ResponseEntity<>(comment, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Create a new comment")
    @ApiResponse(responseCode = "201", description = "Comment created", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))})
    @PostMapping
    public ResponseEntity<String> create(@RequestBody Comment comment) {
        service.saveComment(comment);
        return new ResponseEntity<>("Comment created successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Update a comment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))}),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)})
    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody Comment updatedComment, @PathVariable Integer id) {
        Comment existingComment = service.getCommentById(id);
        if (existingComment != null) {
            updatedComment.setIdComment(id); // Mantiene el ID del comentario existente
            service.saveComment(updatedComment);
            return new ResponseEntity<>("Comment updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
        }
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

    @GetMapping("/users")
    public List<CommentDTO> findAllComments() {
        return commentRepository.findAllComments();
    }
}
