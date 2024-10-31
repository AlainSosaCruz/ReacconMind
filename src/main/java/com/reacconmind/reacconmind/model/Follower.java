package com.reacconmind.reacconmind.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Follower {

    @EmbeddedId
    private FollowerPK id; 

    public Follower() {
    }

    public Follower(FollowerPK idFollower) {
        this.id = idFollower;
    }

    public FollowerPK getIdFollower() {
        return id;
    }

    public void setIdFollower(FollowerPK idFollower) {
        this.id = idFollower;
    }

    public enum FollowingType {
        User,
        Bot
    }
}
