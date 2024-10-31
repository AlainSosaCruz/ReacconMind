package com.reacconmind.reacconmind.dto;

import java.sql.Timestamp;

import com.reacconmind.reacconmind.model.Follower.FollowingType;

public class FollowerDTO {
    private int idUserFollower;
    private int idFollowing;
    private String followingType;
    private Timestamp dateFollowing;

    public FollowerDTO(int i, int j, FollowingType followingType2) {}

    public FollowerDTO(int idUserFollower) {
        this.idUserFollower = idUserFollower;
    }

    public FollowerDTO(int idUserFollower, int idFollowing, String followingType) {
        this.idUserFollower = idUserFollower;
        this.idFollowing = idFollowing;
        this.followingType = followingType;
    }

    public FollowerDTO(int idUserFollower, int idFollowing, String followingType, Timestamp dateFollowing) {
        this.idUserFollower = idUserFollower;
        this.idFollowing = idFollowing;
        this.followingType = followingType;
        this.dateFollowing = dateFollowing;
    }



    

    public FollowerDTO(int idUserFollower2, int idFollowing2) {
        this.idUserFollower = idUserFollower2;
        this.idFollowing = idFollowing2;
    }

    public int getIdUserFollower() {
        return idUserFollower;
    }

    public void setIdUserFollower(int idUserFollower) {
        this.idUserFollower = idUserFollower;
    }

    public int getIdFollowing() {
        return idFollowing;
    }

    public void setIdFollowing(int idFollowing) {
        this.idFollowing = idFollowing;
    }

    public String getFollowingType() {
        return followingType;
    }

    public void setFollowingType(String followingType) {
        this.followingType = followingType;
    }

    public Timestamp getDateFollowing() {
        return dateFollowing;
    }

    public void setDateFollowing(Timestamp dateFollowing) {
        this.dateFollowing = dateFollowing;
    }
}
