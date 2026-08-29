package com.synechisveltiosi.apis.app365.common.auth.mapper;

import com.synechisveltiosi.apis.app365.common.auth.dto.OAuthUserRequest;
import com.synechisveltiosi.apis.app365.common.auth.dto.OAuthUserResponse;
import com.synechisveltiosi.apis.app365.users.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OAuth2Mapper {

    OAuth2Mapper INSTANCE = Mappers.getMapper(OAuth2Mapper.class);

    OAuthUserRequest from(User user);

    OAuthUserResponse from(OAuthUserRequest userRequest);
}
