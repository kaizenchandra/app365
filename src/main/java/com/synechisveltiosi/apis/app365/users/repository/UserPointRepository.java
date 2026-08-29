package com.synechisveltiosi.apis.app365.users.repository;

import com.synechisveltiosi.apis.app365.users.entity.UserPoint;
import com.synechisveltiosi.apis.app365.users.entity.UserPointPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPointRepository extends JpaRepository<UserPoint, UserPointPk> {

    List<UserPoint> findById_UserId_Id(Long userId);

    Optional<UserPoint> findById_UserId_IdAndId_ActionTypeName_Name(Long userId, String actionTypeName);
}
