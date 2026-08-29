package com.synechisveltiosi.apis.app365.users.mapper;

import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import com.synechisveltiosi.apis.app365.users.dto.UserResponse;
import com.synechisveltiosi.apis.app365.users.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserToUserResponseMapper extends AbstractMapper<User, UserResponse> {

    @Override
    public UserResponse map(User user) {
        return UserMapper.INSTANCE.from(user);
    }
}
