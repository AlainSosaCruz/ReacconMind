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

import com.reacconmind.reacconmind.model.TendencyHashtag;
import com.reacconmind.reacconmind.service.TendencyHashtagService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("tendencyHashtags")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
@Tag(name = "Tendency Hashtags", description = "Provides methods for managing tendency and hashtag relationships")
public class TendencyHashtagController {
    @Autowired
    private TendencyHashtagService tendencyHashtagService;

    @Operation(summary = "Get all tendency hashtags")
    @ApiResponse(responseCode = "200", description = "Found tendency hashtags", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TendencyHashtag.class))) })
    @GetMapping
    public List<TendencyHashtag> getAllTendencyHashtags() {
        return tendencyHashtagService.getAllTendencyHashtags();
    }

    @Operation(summary = "Save a new tendency hashtag relationship")
    @ApiResponse(responseCode = "201", description = "Tendency hashtag created successfully")
    @PostMapping
    public ResponseEntity<String> saveTendencyHashtag(@RequestBody TendencyHashtag tendencyHashtag) {
        tendencyHashtagService.saveTendencyHashtag(tendencyHashtag);
        return ResponseEntity.status(HttpStatus.CREATED).body("TendencyHashtag relationship created successfully");
    }
}
