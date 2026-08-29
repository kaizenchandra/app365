package com.synechisveltiosi.apis.app365.actions.mapper;

import com.synechisveltiosi.apis.app365.actions.entity.Medal;
import com.synechisveltiosi.apis.app365.actions.dto.MedalResponse;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class MedalToMedalResponseMapper extends AbstractMapper<Medal, MedalResponse> {

    @Override
    public MedalResponse map(Medal medal) {
        return MedalMapper.INSTANCE.from(medal);
    }
}
