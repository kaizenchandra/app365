package com.synechisveltiosi.apis.app365.slides;

import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "slides")
public class Slide extends BaseEntity {

    private static final long serialVersionUID = 0L;

    @Column(name = "slide_id")
    private String slideId;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "created_at")
    private Date createdAt;

    public String getSlideId() {
        return slideId;
    }

    public void setSlideId(String slideId) {
        this.slideId = slideId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @PrePersist
    public void prePersist() {
        if (slideId == null) slideId = UUID.randomUUID().toString();
        if (createdAt == null) createdAt = new Date();
    }
}
