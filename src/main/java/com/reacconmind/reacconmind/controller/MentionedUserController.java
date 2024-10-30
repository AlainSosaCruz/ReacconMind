package com.reacconmind.reacconmind.controller;

import com.reacconmind.reacconmind.model.MentionedUser;
import com.reacconmind.reacconmind.service.MentionedUserService;
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

@Tag(name = "Mentioned")
@RestController
@RequestMapping("/mentionedUsers")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
public class MentionedUserController {

    @Autowired
    private MentionedUserService service;

    @Operation(summary = "Get all mentioned users")
    @ApiResponse(responseCode = "200", description = "Found Mentioned Users", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MentionedUser.class))) })
    @GetMapping
    public List<MentionedUser> getAll() {
        return service.getAllMentionedUsers();
    }

    @Operation(summary = "Get a mentioned user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mentioned User found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MentionedUser.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Mentioned User not found", content = @Content) })
    @GetMapping("{idPublication}/{idMentionedUser}")
    public ResponseEntity<?> getById(@PathVariable Integer idPublication, @PathVariable Integer idMentionedUser) {
        MentionedUser mentionedUser = service.getMentionedUserById(idPublication, idMentionedUser);
        return mentionedUser != null
                ? new ResponseEntity<>(mentionedUser, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Create a new mentioned user")
    @ApiResponse(responseCode = "201", description = "Mentioned User created", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = MentionedUser.class)) })
    @PostMapping
    public ResponseEntity<String> create(@RequestBody MentionedUser mentionedUser) {
        service.saveMentionedUser(mentionedUser);
        return new ResponseEntity<>("Mentioned User created successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Update a mentioned user by IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mentioned User updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MentionedUser.class)) }),
            @ApiResponse(responseCode = "404", description = "Mentioned User not found", content = @Content) })
    @PutMapping("{idPublication}/{idMentionedUser}")
    public ResponseEntity<?> update(@RequestBody MentionedUser updatedMentionedUser,
                                    @PathVariable Integer idPublication,
                                    @PathVariable Integer idMentionedUser) {
        MentionedUser existingMentionedUser = service.getMentionedUserById(idPublication, idMentionedUser);
        if (existingMentionedUser != null) {
            updatedMentionedUser.setPublication(existingMentionedUser.getPublication());
            updatedMentionedUser.setMentionedUser(existingMentionedUser.getMentionedUser());
            service.saveMentionedUser(updatedMentionedUser);
            return new ResponseEntity<>("Mentioned User updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Mentioned User not found", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a mentioned user by IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mentioned User deleted"),
            @ApiResponse(responseCode = "404", description = "Mentioned User not found") })
    @DeleteMapping("{idPublication}/{idMentionedUser}")
    public ResponseEntity<String> delete(@PathVariable Integer idPublication, @PathVariable Integer idMentionedUser) {
        MentionedUser existingMentionedUser = service.getMentionedUserById(idPublication, idMentionedUser);
        if (existingMentionedUser != null) {
            service.deleteMentionedUser(idPublication, idMentionedUser);
            return new ResponseEntity<>("Mentioned User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Mentioned User not found", HttpStatus.NOT_FOUND);
        }
    }
}
