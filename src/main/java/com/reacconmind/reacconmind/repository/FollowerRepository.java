package com.reacconmind.reacconmind.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reacconmind.reacconmind.model.Follower;
import com.reacconmind.reacconmind.model.FollowerPK;

public interface FollowerRepository extends JpaRepository<Follower, FollowerPK> {
    @Query("SELECT f FROM Follower f WHERE f.id.idUserFollower = :userId")
    Page<Follower> findFollowingsByUserId(@Param("userId") int userId, Pageable pageable);

    @Query("SELECT f FROM Follower f WHERE f.id.idFollowing = :userId AND f.id.followingType = 'User'")
    Page<Follower> findFollowersByUserId(@Param("userId") int userId, Pageable pageable);

}
