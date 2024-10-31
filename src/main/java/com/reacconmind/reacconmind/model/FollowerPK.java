package com.reacconmind.reacconmind.model;

import java.io.Serializable;
import java.util.Objects;

import com.reacconmind.reacconmind.model.Follower.FollowingType;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class FollowerPK implements Serializable {
    @Column(name = "idUserFollower")
    private int idUserFollower;  // The user who follows

    @Column(name = "idFollowing")
    private int idFollowing;  // The followed user or bot
    @Enumerated(EnumType.STRING)
    @Column(name = "followingType")
    private FollowingType followingType;  
    
    public FollowerPK() {}

    public FollowerPK(int idUserFollower, int idFollowing, FollowingType followingType) {
        this.idUserFollower = idUserFollower;
        this.idFollowing = idFollowing;
        this.followingType = followingType;
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

    public FollowingType getFollowingType() {
        return followingType;
    }

    public void setFollowingType(FollowingType followingType) {
        this.followingType = followingType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FollowerPK)) return false;
        FollowerPK that = (FollowerPK) o;
        return idUserFollower == that.idUserFollower &&
               idFollowing == that.idFollowing &&
               followingType.equals(that.followingType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUserFollower, idFollowing, followingType);
    }
}
