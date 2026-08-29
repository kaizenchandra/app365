package com.synechisveltiosi.apis.app365.users.mapper;

import com.synechisveltiosi.apis.app365.actions.dto.AwardsResponse;
import com.synechisveltiosi.apis.app365.actions.dto.MedalResponse;
import com.synechisveltiosi.apis.app365.actions.mapper.MedalMapper;
import com.synechisveltiosi.apis.app365.campaign.dto.MilitantRequest;
import com.synechisveltiosi.apis.app365.users.dto.UserResponse;
import com.synechisveltiosi.apis.app365.users.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class UserMapperDecorator implements UserMapper {

    private static final Logger logger = LoggerFactory.getLogger(UserMapperDecorator.class);

    private final UserMapper mapper;

    public UserMapperDecorator(UserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public UserResponse from(User user) {
        UserResponse userResponse = mapper.from(user);

        // Map the user award
        if (userResponse.getAwards() == null)
            userResponse.setAwards(new AwardsResponse());

        // Map the user points
        if (user.getUserPoints() != null) {
            int points = user.getUserPoints().stream().mapToInt(userPoint -> userPoint.getPoints().intValue()).sum();
            userResponse.getAwards().withPoints(points);
        }

        // Map the user medals
        if (user.getAwards() != null) {
            List<MedalResponse> medals = user.getAwards().stream()
                    .map(award -> award.getId().getMedalId())
                    .map(MedalMapper.INSTANCE::from)
                    .collect(Collectors.toList());

            userResponse.getAwards().withMedals(medals);
        }

        return userResponse;
    }

    @Override
    public MilitantRequest toMilitantRequest(User user, Map<String, Object> address) {
        MilitantRequest militantRequest = mapper.toMilitantRequest(user, address);
        militantRequest.setIdCard(user.getIdCard());

        // Map mobile phone
        if (StringUtils.isNotBlank(user.getPhone())) {
            MilitantRequest.Phone phone = new MilitantRequest.Phone();
            phone.setMobile(user.getPhone());
            phone.setMobileCountryCode(user.getPhoneCountryCode());

            militantRequest.setPhone(phone);
        }

        // Map address
        if (address != null) {
            militantRequest.setAddress(address);
        }

        return militantRequest;
    }
}
