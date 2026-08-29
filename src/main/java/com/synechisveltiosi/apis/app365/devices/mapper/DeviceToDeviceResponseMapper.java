package com.synechisveltiosi.apis.app365.devices.mapper;

import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import com.synechisveltiosi.apis.app365.devices.Device;
import com.synechisveltiosi.apis.app365.devices.DeviceResponse;
import org.springframework.stereotype.Component;

@Component
public class DeviceToDeviceResponseMapper extends AbstractMapper<Device, DeviceResponse> {

    @Override
    public DeviceResponse map(Device device) {
        return DeviceMapper.INSTANCE.from(device);
    }
}
