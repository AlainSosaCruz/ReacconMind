package com.reacconmind.reacconmind.controller;

import com.reacconmind.reacconmind.dto.ReplyDTO;
import com.reacconmind.reacconmind.model.Reply;
import com.reacconmind.reacconmind.repository.ReplyRepository;
import com.reacconmind.reacconmind.service.ReplyService;
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

@Tag(name = "Replies", description = "Operations related to reply management.")
@RestController
@RequestMapping("/replies")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
public class ReplyController {

    @Autowired
    private ReplyService service;

    @Operation(summary = "Get all replies")
    @ApiResponse(responseCode = "200", description = "Found Replies", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Reply.class))) })
    @GetMapping
    public List<Reply> getAll() {
        return service.getAllReplies();
    }

    @Operation(summary = "Get a reply by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reply found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Reply.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid reply ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reply not found", content = @Content) })
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Reply reply = service.getByIdReply(id);
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }

    @Operation(summary = "Create a new reply")
    @ApiResponse(responseCode = "200", description = "Reply created", content = @Content)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Reply reply) {
        service.saveReply(reply);
        return new ResponseEntity<>("Reply created", HttpStatus.OK);
    }

    @Operation(summary = "Update an existing reply")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reply updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid reply ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reply not found", content = @Content) })
    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody Reply reply, @PathVariable Integer id) {
        Reply existingReply = service.getByIdReply(id);
        reply.setIdReply(existingReply.getIdReply());
        service.saveReply(reply);
        return new ResponseEntity<>("Reply updated", HttpStatus.OK);
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

    @Autowired
    private ReplyRepository replyRepository;

    @Operation(summary = "Get all replies for DTO")
    @ApiResponse(responseCode = "200", description = "Found Replies DTO", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReplyDTO.class))) })
    @GetMapping("/users")
    public List<ReplyDTO> findAllReplies() {
        return replyRepository.findAllReplies();
    }
}
