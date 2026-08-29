package com.synechisveltiosi.apis.app365.devices;

import java.util.List;
import java.util.Optional;

public interface DeviceService {

    List<Device> findAll();

    List<Device> findAll(Long userId);

    Optional<Device> findById(String id);

    Device save(Device device);

    void delete(String id);

    void updateFirebaseToken(String id, String newToken);

    void clearFirebaseToken(String id) throws DeviceNotFoundException;
}
