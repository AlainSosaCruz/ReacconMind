package com.reacconmind.reacconmind.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.reacconmind.reacconmind.dto.BotDTO;
import com.reacconmind.reacconmind.dto.FollowerDTO;
import com.reacconmind.reacconmind.model.Follower;
import com.reacconmind.reacconmind.service.FollowerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("ReacconMind/followers")
@Tag(name = "Follower")
public class FollowerController {
    @Autowired
    private FollowerService followerService;

    @Operation(summary = "A user will follow another user or a bot", description = "A user can follow another user as long as it is not him/her, he/she can also follow a bot.")
    @ApiResponse(responseCode = "201", description = "Successfully followed.")
    @PostMapping("/follow")
    public ResponseEntity<String> follow(@RequestBody Follower follower) {
        followerService.follow(follower);
        return ResponseEntity.status(201).body("Successfully followed.");
    }

    @Operation(summary = "A user will unfollow an user", description = "A user can stop following a user.")
    @ApiResponse(responseCode = "200", description = "Unfollowed user.", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BotDTO.class))) })
    @DeleteMapping("/unfollow")
    public ResponseEntity<Void> unfollow(@RequestBody FollowerDTO followerDto) {
        followerService.unfollow(followerDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a list of followings ", description = "A user can stop following a user.")
    @ApiResponse(responseCode = "200", description = "Unfollowed user.", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BotDTO.class))) })
    @GetMapping("/{userId}/followings")
    public ResponseEntity<List<FollowerDTO>> getFollowings(@PathVariable int userId) {
        List<FollowerDTO> followings = followerService.getFollowings(userId);
        return ResponseEntity.ok(followings);
    }

    @Operation(summary = "Get a list of follower ", description = "A user can stop following a user.")
    @ApiResponse(responseCode = "200", description = "Unfollowed user.", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BotDTO.class))) })
    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<FollowerDTO>> getFollowers(@PathVariable int userId) {
        List<FollowerDTO> followers = followerService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }

}
