package com.synechisveltiosi.apis.app365.devices.mapper;

import com.synechisveltiosi.apis.app365.devices.Device;
import com.synechisveltiosi.apis.app365.devices.DeviceRequest;
import com.synechisveltiosi.apis.app365.devices.DeviceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeviceMapper {

    DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);

    @Mapping(source = "deviceId", target = "id")
    DeviceResponse from(Device device);

    Device from(DeviceRequest device);
}
