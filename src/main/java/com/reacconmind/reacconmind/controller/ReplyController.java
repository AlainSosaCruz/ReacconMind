package com.reacconmind.reacconmind.controller;

import com.reacconmind.reacconmind.dto.ReplyDTO;
import com.reacconmind.reacconmind.model.Reply;
import com.reacconmind.reacconmind.repository.ReplyRepository;
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

@Tag(name = "Reply", description = "Operations related to reply management.")
@RestController
@RequestMapping("/replies")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
public class ReplyController {

    @Autowired
    private ReplyService service;

    @Autowired
    private ReplyRepository replyRepository;



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
