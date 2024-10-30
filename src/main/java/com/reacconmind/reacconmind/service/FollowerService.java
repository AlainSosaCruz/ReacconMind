package com.reacconmind.reacconmind.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reacconmind.reacconmind.dto.FollowerDTO;
import com.reacconmind.reacconmind.model.Follower;
import com.reacconmind.reacconmind.model.FollowerPK;
import com.reacconmind.reacconmind.model.Follower.FollowingType;
import com.reacconmind.reacconmind.repository.BotRepository;
import com.reacconmind.reacconmind.repository.FollowerRepository;
import com.reacconmind.reacconmind.repository.UserRepository;

import java.sql.Timestamp;

@Service
public class FollowerService {
    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BotRepository botRepository;

    public FollowerDTO follow(Follower follower) {
        FollowerPK followerPK = follower.getIdFollower();
        validateFollowerIds(followerPK);
        validateFollowerExists(followerPK.getIdUserFollower());
        validateNotFollowingSelf(followerPK);
        validateTargetExists(followerPK, "follow");

        followerRepository.save(follower);

        return new FollowerDTO(
                followerPK.getIdUserFollower(),
                followerPK.getIdFollowing(),
                followerPK.getFollowingType().name(),
                new Timestamp(System.currentTimeMillis()));
    }
    
    public void unfollow(FollowerDTO followerDto) {
        FollowingType type;
        try {
            type = FollowingType.valueOf(followerDto.getFollowingType());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid following type: " + followerDto.getFollowingType());
        }
    
        FollowerPK followerPK = new FollowerPK(followerDto.getIdUserFollower(), followerDto.getIdFollowing(), type);
    
        validateFollowerIds(followerPK);
        validateFollowerExists(followerPK.getIdUserFollower());
        validateTargetExists(followerPK, "unfollow");

        followerRepository.deleteById(followerPK);
    }

    public List<FollowerDTO> getFollowings(int userId) {
        List<Follower> followings = followerRepository.findFollowingsByUserId(userId);
        return followings.stream()
                .map(f -> new FollowerDTO(f.getIdFollower().getIdUserFollower(), f.getIdFollower().getIdFollowing()))                
                .collect(Collectors.toList());
    }


    public List<FollowerDTO> getFollowers(int userId) {
         List<Follower> followers = followerRepository.findFollowersByUserId(userId);
         return followers.stream()
                 .map(f -> new FollowerDTO(f.getIdFollower().getIdUserFollower()))  //Constructor con solo idUserFollower
                 .collect(Collectors.toList());
    }
    
    // Validations
    private void validateFollowerIds(FollowerPK followerPK) {
        if (followerPK.getIdUserFollower() <= 0 || followerPK.getIdFollowing() <= 0) {
            throw new IllegalArgumentException("IDs must be greater than zero.");
        }
    }

    private void validateFollowerExists(int idUserFollower) {
        if (!userRepository.existsById(idUserFollower)) {
            throw new IllegalArgumentException("The follower does not exist.");
        }
    }

    private void validateNotFollowingSelf(FollowerPK followerPK) {
        if (followerPK.getIdUserFollower() == followerPK.getIdFollowing()) {
            throw new IllegalArgumentException("A user cannot follow themselves.");
        }
    }

    private void validateTargetExists(FollowerPK followerPK, String action) {
        boolean targetExists = switch (followerPK.getFollowingType()) {
            case User -> userRepository.existsById(followerPK.getIdFollowing());
            case Bot -> botRepository.existsById(followerPK.getIdFollowing());
            default -> throw new IllegalArgumentException("Invalid following type.");
        };
    
        if (!targetExists) {
            throw new IllegalArgumentException(
                    "The " + followerPK.getFollowingType().name().toLowerCase() + " to " + action + " does not exist.");
        }
    }
    

}