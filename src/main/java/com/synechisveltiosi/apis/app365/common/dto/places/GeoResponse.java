package com.synechisveltiosi.apis.app365.common.dto.places;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeoResponse {

    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("lon")
    private Double lon;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public GeoResponse withLat(Double lat) {
        this.lat = lat;
        return this;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public GeoResponse withLon(Double lon) {
        this.lon = lon;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("lat", lat)
                .append("lon", lon)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(lat)
                .append(lon)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GeoResponse)) {
            return false;
        }

        GeoResponse rhs = ((GeoResponse) other);
        return new EqualsBuilder()
                .append(lat, rhs.lat)
                .append(lon, rhs.lon)
                .isEquals();
    }
}
