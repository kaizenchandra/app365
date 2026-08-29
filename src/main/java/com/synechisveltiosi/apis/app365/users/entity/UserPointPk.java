package com.synechisveltiosi.apis.app365.users.entity;

import com.synechisveltiosi.apis.app365.actions.entity.ActionType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class UserPointPk implements Serializable {

    private static final long serialVersionUID = 0L;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "action_type_name", nullable = false, insertable = false, updatable = false)
    private ActionType actionTypeName;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User userId;

    public ActionType getActionTypeName() {
        return actionTypeName;
    }

    public void setActionTypeName(ActionType actionTypeName) {
        this.actionTypeName = actionTypeName;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserPointPk that = (UserPointPk) o;

        return new EqualsBuilder()
                .append(actionTypeName, that.actionTypeName)
                .append(userId, that.userId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(actionTypeName)
                .append(userId)
                .toHashCode();
    }
}
