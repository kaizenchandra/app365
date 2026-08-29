package com.synechisveltiosi.apis.app365.actions.entity;

import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "medal_rules", uniqueConstraints = @UniqueConstraint(columnNames = {"medal_id", "action_type_name"}))
public class MedalRule extends BaseEntity {

    @Column(name = "medal_rule_id", nullable = false, unique = true)
    private String medalRuleId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "medal_id", nullable = false)
    private Medal medalId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "action_type_name", nullable = false)
    private ActionType actionTypeName;

    @Column(name = "total")
    private Double total = 0D;

    @Transient
    private boolean matched = false;

    public String getMedalRuleId() {
        return medalRuleId;
    }

    public void setMedalRuleId(String medalRuleId) {
        this.medalRuleId = medalRuleId;
    }

    public Medal getMedalId() {
        return medalId;
    }

    public void setMedalId(Medal medalId) {
        this.medalId = medalId;
    }

    public ActionType getActionTypeName() {
        return actionTypeName;
    }

    public void setActionTypeName(ActionType actionTypeName) {
        this.actionTypeName = actionTypeName;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double minCount) {
        this.total = minCount;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    @PrePersist
    public void prePersist() {
        if (medalRuleId == null) medalRuleId = UUID.randomUUID().toString();
    }
}
