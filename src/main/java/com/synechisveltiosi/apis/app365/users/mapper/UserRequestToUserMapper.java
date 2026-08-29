package com.synechisveltiosi.apis.app365.users.mapper;

import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.synechisveltiosi.apis.app365.users.dto.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserRequestToUserMapper extends AbstractMapper<UserRequest, User> {

    @Override
    public User map(UserRequest userRequest) {
        return UserMapper.INSTANCE.from(userRequest);
    }
}
