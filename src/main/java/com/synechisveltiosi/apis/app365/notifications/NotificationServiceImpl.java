package com.synechisveltiosi.apis.app365.notifications;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Optional<Notification> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Notification id should not be null or 0");

        return notificationRepository.findById(id);
    }

    @Override
    public Optional<Notification> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("Notification id should not be null or blank");

        return notificationRepository.findByNotificationId(id);
    }

    @Override
    public Page<Notification> findAll(Pageable pageable) {
        pageable = pageable == null ? Pageable.unpaged() : pageable;

        return notificationRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        Optional<Notification> notificationOptional = findById(id);
        if (!notificationOptional.isPresent()) throw new NotificationNotFoundException();

        // Delete the notification
        notificationRepository.deleteById(notificationOptional.get().getId());
    }

    @Transactional
    @Override
    public void deleteAllByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        notificationRepository.deleteAllByUserId_Id(userId);
    }
}
