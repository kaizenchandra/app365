package com.synechisveltiosi.apis.app365.users.mapper;

import com.synechisveltiosi.apis.app365.address.mapper.AddressMapper;
import com.synechisveltiosi.apis.app365.campaign.dto.MilitantRequest;
import com.synechisveltiosi.apis.app365.common.auth.dto.OAuthUserRequest;
import com.synechisveltiosi.apis.app365.users.dto.UserRequest;
import com.synechisveltiosi.apis.app365.users.dto.UserResponse;
import com.synechisveltiosi.apis.app365.users.entity.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper(uses = {AddressMapper.class})
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User from(UserRequest userRequest);

    @Mappings({
            @Mapping(source = "userId", target = "id"),
            @Mapping(target = "awards", ignore = true)
    })
    UserResponse from(User user);

    @Mapping(expression = "java((user.getFirstName() == null ? \"\" : user.getFirstName() + \" \") + " +
            "(user.getLastName() == null ? \"\" : user.getLastName()))", target = "name")
    OAuthUserRequest toOAuthUserRequest(User user);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "phone", ignore = true),
            @Mapping(target = "address", ignore = true)
    })
    MilitantRequest toMilitantRequest(User user, Map<String, Object> address);
}
