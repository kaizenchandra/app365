package com.synechisveltiosi.apis.app365.notifications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface NotificationService {

    Optional<Notification> findById(Long id);

    Optional<Notification> findById(String id);

    Page<Notification> findAll(Pageable pageable);

    Notification save(Notification notification);

    void deleteById(String id);

    void deleteAllByUserId(Long userId);
}
