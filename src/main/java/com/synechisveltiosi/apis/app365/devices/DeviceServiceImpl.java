package com.synechisveltiosi.apis.app365.devices;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.synechisveltiosi.apis.app365.users.exception.UserNotFoundException;
import com.synechisveltiosi.apis.app365.users.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final UserService userService;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository, UserService userService) {
        this.deviceRepository = deviceRepository;
        this.userService = userService;
    }

    @Override
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    @Override
    public List<Device> findAll(Long userId) {
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        return deviceRepository.findByUserId(userOptional.get());
    }

    @Override
    public Optional<Device> findById(String id) {
        if (StringUtils.isEmpty(id)) throw new BadRequestException("Invalid device id.");

        return deviceRepository.findByDeviceId(id);
    }

    @Override
    public Device save(Device device) {
        device.setDeviceId(device.getDeviceId().toLowerCase());
        return deviceRepository.save(device);
    }

    @Override
    public void delete(String id) {
        if (StringUtils.isEmpty(id)) throw new BadRequestException("Invalid device id.");

        deviceRepository.deleteByDeviceId(id);
    }

    @Override
    public void updateFirebaseToken(String id, String newToken) {
        if (StringUtils.isEmpty(newToken)) throw new BadRequestException("Invalid token.");

        if (StringUtils.isEmpty(id)) throw new BadRequestException("Invalid device id.");

        Optional<Device> deviceOptional = this.findById(id);
        if (!deviceOptional.isPresent()) throw new DeviceNotFoundException();

        // Update the firebase token
        Device device = deviceOptional.get();
        device.setFirebaseToken(newToken);

        // Update the device
        this.save(device);
    }

    @Override
    public void clearFirebaseToken(String id) throws DeviceNotFoundException {
        if (StringUtils.isEmpty(id)) throw new BadRequestException("Invalid device id.");

        Optional<Device> deviceOptional = this.findById(id);
        if (!deviceOptional.isPresent()) throw new DeviceNotFoundException();

        // Clear the firebase token
        Device device = deviceOptional.get();
        device.setFirebaseToken(null);

        // Update the device
        this.save(device);
    }
}
