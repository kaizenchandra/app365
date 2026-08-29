package com.synechisveltiosi.apis.app365.actions.entity;

import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "medals")
public class Medal extends BaseEntity {

    private static final long serialVersionUID = 0L;

    public static final Integer DEFAULT_PAGE = 0;
    public static final Integer MAX_PAGE_SIZE = 25;
    public static final List<String> SORTABLE_FIELDS = Arrays.asList(Sortable.NAME, Sortable.POINTS);

    @Column(name = "medal_id")
    private String medalId;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "instructions", columnDefinition = "text")
    private String instructions;

    @Column(name = "benefits", columnDefinition = "text")
    private String benefits;

    @Column(name = "points")
    private Long points;

    @Column(name = "image")
    private String image;

    @Column(name = "created_at")
    private Date createdAt;

    public String getMedalId() {
        return medalId;
    }

    public void setMedalId(String medalId) {
        this.medalId = medalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Medal medal = (Medal) o;

        return new EqualsBuilder()
                .append(medalId, medal.medalId)
                .append(name, medal.name)
                .append(description, medal.description)
                .append(instructions, medal.instructions)
                .append(benefits, medal.benefits)
                .append(points, medal.points)
                .append(image, medal.image)
                .append(createdAt, medal.createdAt)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(medalId)
                .append(name)
                .append(description)
                .append(instructions)
                .append(benefits)
                .append(points)
                .append(image)
                .append(createdAt)
                .toHashCode();
    }

    @PrePersist
    public void prePersist() {
        if (medalId == null) medalId = UUID.randomUUID().toString();
        if (createdAt == null) createdAt = new Date();
    }

    public interface Sortable {
        String NAME = "name";
        String POINTS = "points";
        String DEFAULT_SORT = "-" + POINTS;
    }
}
