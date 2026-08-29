package com.synechisveltiosi.apis.app365.actions.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synechisveltiosi.apis.app365.common.util.date.DateFormatUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedalResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("picture")
    private String picture;

    @JsonProperty("point")
    private Integer points;

    @JsonProperty("createdAt")
    @JsonFormat(pattern = DateFormatUtils.ISO_8601_DATETIME_TIME_ZONE_STRING_FORMAT, shape = JsonFormat.Shape.STRING)
    private Date createdAt;

    @JsonProperty("instructions")
    private String instructions;

    @JsonProperty("benefits")
    private String benefits;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MedalResponse withId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MedalResponse withName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MedalResponse withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public MedalResponse withPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public MedalResponse withPoint(Integer point) {
        this.points = point;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public MedalResponse withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public MedalResponse withInstructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public MedalResponse withBenefits(String benefits) {
        this.benefits = benefits;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("description", description)
                .append("picture", picture)
                .append("points", points)
                .append("createdAt", createdAt)
                .append("instructions", instructions)
                .append("benefits", benefits)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(benefits)
                .append(createdAt)
                .append(instructions)
                .append(name)
                .append(description)
                .append(id)
                .append(picture)
                .append(points)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MedalResponse)) {
            return false;
        }

        MedalResponse rhs = ((MedalResponse) other);
        return new EqualsBuilder()
                .append(benefits, rhs.benefits)
                .append(createdAt, rhs.createdAt)
                .append(instructions, rhs.instructions)
                .append(name, rhs.name)
                .append(description, rhs.description)
                .append(id, rhs.id)
                .append(picture, rhs.picture)
                .append(points, rhs.points)
                .isEquals();
    }
}
