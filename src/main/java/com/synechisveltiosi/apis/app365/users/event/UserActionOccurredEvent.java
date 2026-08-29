package com.synechisveltiosi.apis.app365.users.event;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class UserActionOccurredEvent {

    private final Long userId;
    private final String[] actionTypeNames;

    public UserActionOccurredEvent(Long userId, String... actionTypeNames) {
        this.userId = userId;
        this.actionTypeNames = actionTypeNames;
    }

    public Long getUserId() {
        return userId;
    }

    public String[] getActionTypeName() {
        return actionTypeNames;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userId", userId)
                .append("actionTypeNames", actionTypeNames)
                .toString();
    }
}
