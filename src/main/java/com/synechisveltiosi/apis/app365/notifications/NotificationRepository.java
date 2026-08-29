package com.synechisveltiosi.apis.app365.notifications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByNotificationId(String notificationId);

    Page<Notification> findAllByOrderByCreatedAtDesc(Pageable pageable);

    void deleteAllByUserId_Id(Long userId);
}
