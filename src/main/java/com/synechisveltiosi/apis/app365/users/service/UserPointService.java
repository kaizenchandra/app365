package com.synechisveltiosi.apis.app365.users.service;

import com.synechisveltiosi.apis.app365.users.entity.UserPoint;

import java.util.List;
import java.util.Optional;

public interface UserPointService {

    List<UserPoint> findByUserId(Long userId);

    Optional<UserPoint> findByUserIdAndActionTypeName(Long userId, String actionTypeName);

    UserPoint save(UserPoint userPoint);

    void calculatePoints(Long userId, String actionTypeName);
}
