package com.synechisveltiosi.apis.app365.actions.entity;

import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "point_rules")
public class PointRule extends BaseEntity {

    @Column(name = "point_rule_id", nullable = false, unique = true)
    private String pointRuleId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "action_type_name", nullable = false, unique = true)
    private ActionType actionTypeName;

    @Column(name = "frequency")
    private Double frequency = 0D;

    @Column(name = "points")
    private Long points = 0L;

    public String getPointRuleId() {
        return pointRuleId;
    }

    public void setPointRuleId(String pointRuleId) {
        this.pointRuleId = pointRuleId;
    }

    public ActionType getActionTypeName() {
        return actionTypeName;
    }

    public void setActionTypeName(ActionType actionTypeName) {
        this.actionTypeName = actionTypeName;
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double minCount) {
        this.frequency = minCount;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    @PrePersist
    public void prePersist() {
        if (pointRuleId == null) pointRuleId = UUID.randomUUID().toString();
    }
}
