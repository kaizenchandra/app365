package com.synechisveltiosi.apis.app365.devices;

import com.synechisveltiosi.apis.app365.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findByUserId(User user);

    Optional<Device> findByDeviceId(String deviceId);

    void deleteByDeviceId(String id);
}
