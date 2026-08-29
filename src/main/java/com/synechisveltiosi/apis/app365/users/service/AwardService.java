package com.synechisveltiosi.apis.app365.users.service;

import com.synechisveltiosi.apis.app365.users.entity.Award;

import java.util.List;
import java.util.Optional;

public interface AwardService {

    List<Award> findByUserId(Long userId);

    List<Award> findByMedalId(Long medalId);

    Optional<Award> findByUserIdAndMedalId(Long userId, Long medalId);

    Award save(Award award);

    void deleteByUserIdAndMedalId(Long userId, Long medalId);

    void determineUserMedals(Long userId);
}
