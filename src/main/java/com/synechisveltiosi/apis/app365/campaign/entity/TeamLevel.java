package com.synechisveltiosi.apis.app365.campaign.entity;

public class TeamLevel {

    private Integer level;
    private Integer memberCount;

    public TeamLevel() {

    }

    public TeamLevel(Integer level, Integer memberCount) {
        this.level = level;
        this.memberCount = memberCount;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }
}
