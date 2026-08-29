
package com.synechisveltiosi.apis.app365.candidates.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PoliticalParty {

    @JsonProperty("name")
    private String name;

    @JsonProperty("nameAbbr")
    private String nameAbbr;

    @JsonProperty("bannerPicture")
    private String bannerPicture;

    @JsonProperty("slogan")
    private String slogan;

    @JsonProperty("position")
    private String position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PoliticalParty withName(String name) {
        this.name = name;
        return this;
    }

    public String getNameAbbr() {
        return nameAbbr;
    }

    public void setNameAbbr(String nameAbbr) {
        this.nameAbbr = nameAbbr;
    }

    public PoliticalParty withNameAbbr(String nameAbbr) {
        this.nameAbbr = nameAbbr;
        return this;
    }

    public String getBannerPicture() {
        return bannerPicture;
    }

    public void setBannerPicture(String bannerPicture) {
        this.bannerPicture = bannerPicture;
    }

    public PoliticalParty withBannerPicture(String bannerPicture) {
        this.bannerPicture = bannerPicture;
        return this;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public PoliticalParty withSlogan(String slogan) {
        this.slogan = slogan;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public PoliticalParty withPosition(String position) {
        this.position = position;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("nameAbbr", nameAbbr)
                .append("bannerPicture", bannerPicture)
                .append("slogan", slogan)
                .append("position", position)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(nameAbbr)
                .append(bannerPicture)
                .append(slogan)
                .append(position)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PoliticalParty)) {
            return false;
        }

        PoliticalParty rhs = ((PoliticalParty) other);
        return new EqualsBuilder()
                .append(name, rhs.name)
                .append(nameAbbr, rhs.nameAbbr)
                .append(bannerPicture, rhs.bannerPicture)
                .append(slogan, rhs.slogan)
                .append(position, rhs.position)
                .isEquals();
    }
}
