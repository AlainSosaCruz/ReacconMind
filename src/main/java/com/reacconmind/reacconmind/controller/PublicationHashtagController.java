package com.reacconmind.reacconmind.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reacconmind.reacconmind.model.PublicationHashtag;
import com.reacconmind.reacconmind.service.PublicationHashtagService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("publicationHashtags")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
@Tag(name = "Publication Hashtags", description = "Provides methods for managing publication and hashtag relationships")
public class PublicationHashtagController {
    @Autowired
    private PublicationHashtagService publicationHashtagService;

    @Operation(summary = "Get all publication hashtags")
    @ApiResponse(responseCode = "200", description = "Found publication hashtags", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PublicationHashtag.class))) })
    @GetMapping
    public List<PublicationHashtag> getAllPublicationHashtags() {
        return publicationHashtagService.getAllPublicationHashtags();
    }

    @Operation(summary = "Save a new publication hashtag relationship")
    @ApiResponse(responseCode = "201", description = "Publication hashtag created successfully")
    @PostMapping
    public ResponseEntity<String> savePublicationHashtag(@RequestBody PublicationHashtag publicationHashtag) {
        publicationHashtagService.savePublicationHashtag(publicationHashtag);
        return ResponseEntity.status(HttpStatus.CREATED).body("PublicationHashtag relationship created successfully");
    }
}
