package com.synechisveltiosi.apis.app365.campaign.mapper;

import com.synechisveltiosi.apis.app365.campaign.dto.VolunteerResponse;
import com.synechisveltiosi.apis.app365.campaign.entity.Volunteer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper

public interface VolunteerMapper {

    VolunteerMapper INSTANCE = Mappers.getMapper(VolunteerMapper.class);

    @Mappings({
            @Mapping(source = "userId.userId", target = "id"),
            @Mapping(source = "userId.idCard", target = "idCard"),
            @Mapping(source = "userId.firstName", target = "firstName"),
            @Mapping(source = "userId.lastName", target = "lastName"),
            @Mapping(source = "userId.profilePicture", target = "profilePicture"),
            @Mapping(source = "userId.email", target = "email"),
            @Mapping(source = "userId.phone", target = "phone")
    })
    VolunteerResponse from(Volunteer volunteer);
}
