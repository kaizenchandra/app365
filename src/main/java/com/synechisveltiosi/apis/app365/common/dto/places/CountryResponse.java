package com.synechisveltiosi.apis.app365.common.dto.places;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("iso2")
    private String iso2;

    @JsonProperty("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CountryResponse withId(String id) {
        this.id = id;
        return this;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public CountryResponse withIso2(String iso2) {
        this.iso2 = iso2;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryResponse withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("iso2", iso2)
                .append("name", name)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name).append(id)
                .append(iso2)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CountryResponse)) {
            return false;
        }

        CountryResponse rhs = ((CountryResponse) other);
        return new EqualsBuilder()
                .append(name, rhs.name)
                .append(id, rhs.id)
                .append(iso2, rhs.iso2)
                .isEquals();
    }
}
