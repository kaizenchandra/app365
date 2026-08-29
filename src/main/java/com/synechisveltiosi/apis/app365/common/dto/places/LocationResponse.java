
package com.synechisveltiosi.apis.app365.common.dto.places;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationResponse {

    @JsonProperty("address")
    private String address;

    @JsonProperty("geo")
    private GeoResponse geo;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocationResponse withAddress(String address) {
        this.address = address;
        return this;
    }

    public GeoResponse getGeo() {
        return geo;
    }

    public void setGeo(GeoResponse geo) {
        this.geo = geo;
    }

    public LocationResponse withGeo(GeoResponse geo) {
        this.geo = geo;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("address", address).append("geo", geo).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(geo).append(address).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof LocationResponse)) {
            return false;
        }
        LocationResponse rhs = ((LocationResponse) other);
        return new EqualsBuilder().append(geo, rhs.geo).append(address, rhs.address).isEquals();
    }
}
