package com.synechisveltiosi.apis.app365.common.service.social;

import com.synechisveltiosi.apis.app365.users.entity.User;

public interface SocialNetworkService {

    User loadWithToken(String token);
}
