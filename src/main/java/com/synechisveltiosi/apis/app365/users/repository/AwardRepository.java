package com.synechisveltiosi.apis.app365.users.repository;

import com.synechisveltiosi.apis.app365.users.entity.Award;
import com.synechisveltiosi.apis.app365.users.entity.AwardPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AwardRepository extends JpaRepository<Award, AwardPk> {

    List<Award> findById_UserId_Id(Long userId);

    List<Award> findById_MedalId_Id(Long medalId);

    Optional<Award> findById_UserId_IdAndId_MedalId_Id(Long userId, Long medalId);

    void deleteById_UserId_IdAndId_MedalId_Id(Long userId, Long medalId);
}
