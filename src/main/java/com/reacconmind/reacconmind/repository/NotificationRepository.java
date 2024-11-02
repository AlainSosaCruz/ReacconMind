package com.reacconmind.reacconmind.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.reacconmind.reacconmind.model.Notification;
import com.reacconmind.reacconmind.model.NotificationStatus;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query("SELECT n FROM Notification n WHERE n.idUser.idUser = :userId")
    Page<Notification> findAllByUserId(@Param("userId") Integer userId, Pageable pageable);

    @Query("SELECT n FROM Notification n WHERE n.state = :status AND n.idUser.idUser = :userId")
    Page<Notification> findUnreadNotificationsByUserId(@Param("userId") Integer userId,
            @Param("status") NotificationStatus status, Pageable pageable);

}