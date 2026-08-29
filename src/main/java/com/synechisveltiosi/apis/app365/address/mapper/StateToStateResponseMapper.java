package com.synechisveltiosi.apis.app365.address.mapper;

import com.synechisveltiosi.apis.app365.address.entity.State;
import com.synechisveltiosi.apis.app365.common.dto.places.StateResponse;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class StateToStateResponseMapper extends AbstractMapper<State, StateResponse> {

    @Override
    public StateResponse map(State state) {
        return StateMapper.INSTANCE.from(state);
    }
}
