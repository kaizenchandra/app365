package com.synechisveltiosi.apis.app365.news.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synechisveltiosi.apis.app365.common.dto.DefaultMetaResponse;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.CursorResponse;
import com.synechisveltiosi.apis.app365.common.util.date.DateFormatUtils;
import com.synechisveltiosi.apis.app365.users.dto.UserActionSummaryResponse;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("createdAt")
    @JsonFormat(pattern = DateFormatUtils.ISO_8601_DATETIME_TIME_ZONE_STRING_FORMAT, shape = JsonFormat.Shape.STRING)
    private Date createdAt;

    @JsonProperty("coverPicture")
    private String coverPicture;

    @JsonProperty("isFlagged")
    private Boolean flagged;

    @JsonProperty("summary")
    private UserActionSummaryResponse summary;

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

    public NewsResponse withId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NewsResponse withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NewsResponse withContent(String content) {
        this.content = content;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public NewsResponse withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public NewsResponse withCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
        return this;
    }

    public Boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(Boolean flagged) {
        this.flagged = flagged;
    }

    public NewsResponse withFlagged(Boolean flagged) {
        this.flagged = flagged;
        return this;
    }

    public UserActionSummaryResponse getSummary() {
        return summary;
    }

    public void setSummary(UserActionSummaryResponse summary) {
        this.summary = summary;
    }

    public NewsResponse withSummary(UserActionSummaryResponse summary) {
        this.summary = summary;
        return this;
    }

    public DefaultMetaResponse getMeta() {
        return meta;
    }

    public void setMeta(DefaultMetaResponse meta) {
        this.meta = meta;
    }

    public NewsResponse withMeta(DefaultMetaResponse meta) {
        this.meta = meta;
        return this;
    }

    public CursorResponse getCursor() {
        return cursor;
    }

    public void setCursor(CursorResponse cursor) {
        this.cursor = cursor;
    }

    public NewsResponse withCursor(CursorResponse cursor) {
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
                .append("coverPicture", coverPicture)
                .append("flagged", flagged)
                .append("summary", summary)
                .append("meta", meta)
                .append("cursor", cursor)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(summary)
                .append(cursor)
                .append(createdAt)
                .append(coverPicture)
                .append(meta)
                .append(id)
                .append(title)
                .append(content)
                .append(flagged)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NewsResponse)) {
            return false;
        }

        NewsResponse rhs = ((NewsResponse) other);
        return new EqualsBuilder()
                .append(summary, rhs.summary)
                .append(cursor, rhs.cursor)
                .append(createdAt, rhs.createdAt)
                .append(coverPicture, rhs.coverPicture)
                .append(meta, rhs.meta)
                .append(id, rhs.id)
                .append(title, rhs.title)
                .append(content, rhs.content)
                .append(flagged, rhs.flagged)
                .isEquals();
    }
}
