package com.synechisveltiosi.apis.app365.campaign.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamMemberSummaryResponse {

    @JsonProperty("levelCount")
    private Integer levelCount;

    @JsonProperty("memberCount")
    private Integer memberCount;

    public Integer getLevelCount() {
        return levelCount;
    }

    public void setLevelCount(Integer levelCount) {
        this.levelCount = levelCount;
    }

    public TeamMemberSummaryResponse withLevelCount(Integer levelCount) {
        this.levelCount = levelCount;
        return this;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public TeamMemberSummaryResponse withMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("levelCount", levelCount)
                .append("memberCount", memberCount)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(levelCount)
                .append(memberCount)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TeamMemberSummaryResponse)) {
            return false;
        }

        TeamMemberSummaryResponse rhs = ((TeamMemberSummaryResponse) other);
        return new EqualsBuilder()
                .append(levelCount, rhs.levelCount)
                .append(memberCount, rhs.memberCount)
                .isEquals();
    }
}
