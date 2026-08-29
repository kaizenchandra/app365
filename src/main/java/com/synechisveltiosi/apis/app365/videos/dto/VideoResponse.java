package com.synechisveltiosi.apis.app365.videos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synechisveltiosi.apis.app365.common.dto.DefaultMetaResponse;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.CursorResponse;
import com.synechisveltiosi.apis.app365.common.util.date.DateFormatUtils;
import com.synechisveltiosi.apis.app365.users.dto.UserActionSummaryResponse;
import com.synechisveltiosi.apis.app365.users.dto.UserOwnerResponse;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("createdAt")
    @JsonFormat(pattern = DateFormatUtils.ISO_8601_DATETIME_TIME_ZONE_STRING_FORMAT, shape = JsonFormat.Shape.STRING)
    private Date createdAt;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("url")
    private String url;

    @JsonProperty("summary")
    private UserActionSummaryResponse summary;

    @JsonProperty("owner")
    private UserOwnerResponse owner;

    @JsonProperty("meta")
    private DefaultMetaResponse meta;

    @JsonProperty("cursor")
    private CursorResponse cursor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VideoResponse withId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public VideoResponse withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public VideoResponse withContent(String content) {
        this.content = content;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public VideoResponse withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public VideoResponse withThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public VideoResponse withUrl(String url) {
        this.url = url;
        return this;
    }

    public UserActionSummaryResponse getSummary() {
        return summary;
    }

    public void setSummary(UserActionSummaryResponse summary) {
        this.summary = summary;
    }

    public VideoResponse withSummary(UserActionSummaryResponse summary) {
        this.summary = summary;
        return this;
    }

    public UserOwnerResponse getOwner() {
        return owner;
    }

    public void setOwner(UserOwnerResponse owner) {
        this.owner = owner;
    }

    public VideoResponse withOwner(UserOwnerResponse owner) {
        this.owner = owner;
        return this;
    }

    public DefaultMetaResponse getMeta() {
        return meta;
    }

    public void setMeta(DefaultMetaResponse meta) {
        this.meta = meta;
    }

    public VideoResponse withMeta(DefaultMetaResponse meta) {
        this.meta = meta;
        return this;
    }

    public CursorResponse getCursor() {
        return cursor;
    }

    public void setCursor(CursorResponse cursor) {
        this.cursor = cursor;
    }

    public VideoResponse withCursor(CursorResponse cursor) {
        this.cursor = cursor;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("content", content)
                .append("createdAt", createdAt)
                .append("thumbnail", thumbnail)
                .append("summary", summary)
                .append("owner", owner)
                .append("meta", meta)
                .append("cursor", cursor)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(summary)
                .append(cursor)
                .append(meta)
                .append(owner)
                .append(createdAt)
                .append(thumbnail)
                .append(id)
                .append(title)
                .append(content)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof VideoResponse)) {
            return false;
        }

        VideoResponse rhs = ((VideoResponse) other);
        return new EqualsBuilder()
                .append(summary, rhs.summary)
                .append(cursor, rhs.cursor)
                .append(meta, rhs.meta)
                .append(owner, rhs.owner)
                .append(createdAt, rhs.createdAt)
                .append(thumbnail, rhs.thumbnail)
                .append(id, rhs.id)
                .append(title, rhs.title)
                .append(content, rhs.content)
                .isEquals();
    }
}
