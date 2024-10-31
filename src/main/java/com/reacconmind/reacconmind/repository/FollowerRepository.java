package com.reacconmind.reacconmind.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reacconmind.reacconmind.model.Follower;
import com.reacconmind.reacconmind.model.FollowerPK;

public interface FollowerRepository extends JpaRepository<Follower, FollowerPK> {
    @Query("SELECT f FROM Follower f WHERE f.id.idUserFollower = :userId")
    List<Follower> findFollowingsByUserId(@Param("userId") int userId);

    @Query("SELECT f FROM Follower f WHERE f.id.idFollowing = :userId AND f.id.followingType = 'User'")
    List<Follower> findFollowersByUserId(@Param("userId") int userId);

}
