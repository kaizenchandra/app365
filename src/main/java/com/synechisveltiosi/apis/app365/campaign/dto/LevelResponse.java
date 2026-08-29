
package com.synechisveltiosi.apis.app365.campaign.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LevelResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("level")
    private Integer level;

    @JsonProperty("memberCount")
    private Integer memberCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LevelResponse withId(String id) {
        this.id = id;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public LevelResponse withLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public LevelResponse withMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("level", level)
                .append("memberCount", memberCount)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(level)
                .append(memberCount)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LevelResponse)) {
            return false;
        }

        LevelResponse rhs = ((LevelResponse) other);
        return new EqualsBuilder()
                .append(id, rhs.id)
                .append(level, rhs.level)
                .append(memberCount, rhs.memberCount)
                .isEquals();
    }
}
