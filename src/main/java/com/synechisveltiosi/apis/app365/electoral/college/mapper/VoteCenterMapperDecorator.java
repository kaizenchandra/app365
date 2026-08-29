package com.synechisveltiosi.apis.app365.electoral.college.mapper;

import com.synechisveltiosi.apis.app365.common.dto.places.GeoResponse;
import com.synechisveltiosi.apis.app365.common.dto.places.LocationResponse;
import com.synechisveltiosi.apis.app365.electoral.college.VoteCenter;
import com.synechisveltiosi.apis.app365.electoral.college.dto.VoteCenterResponse;

public abstract class VoteCenterMapperDecorator implements VoteCenterMapper {

    private final VoteCenterMapper mapper;

    public VoteCenterMapperDecorator(VoteCenterMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public VoteCenterResponse from(VoteCenter voteCenter) {
        VoteCenterResponse response = mapper.from(voteCenter);

        // Set the location object
        if (voteCenter.getAddress() != null || voteCenter.getLocationLatitude() != null) {
            response.setLocation(new LocationResponse());

            // Set the address
            response.getLocation().withAddress(voteCenter.getAddress());

            // Set location
            if (voteCenter.getLocationLatitude() != null) {
                response.getLocation().withGeo(new GeoResponse()
                        .withLat(voteCenter.getLocationLatitude())
                        .withLon(voteCenter.getLocationLongitude()));
            }
        }

        return response;
    }
}
