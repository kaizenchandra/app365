
package com.synechisveltiosi.apis.app365.notifications;

import com.synechisveltiosi.apis.app365.common.util.date.DateFormatUtils;
import com.synechisveltiosi.apis.app365.news.dto.ArticleResponse;
import com.synechisveltiosi.apis.app365.users.dto.UserOwnerResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("createdAt")
    @JsonFormat(pattern = DateFormatUtils.ISO_8601_DATETIME_TIME_ZONE_STRING_FORMAT, shape = JsonFormat.Shape.STRING)
    private Date createdAt;

    @JsonProperty("type")
    private String type;

    @JsonProperty("owner")
    private UserOwnerResponse owner;

    @JsonProperty("articles")
    private List<ArticleResponse> articles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NotificationResponse withId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NotificationResponse withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NotificationResponse withContent(String content) {
        this.content = content;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public NotificationResponse withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public NotificationResponse withType(String type) {
        this.type = type;
        return this;
    }

    public UserOwnerResponse getOwner() {
        return owner;
    }

    public void setOwner(UserOwnerResponse owner) {
        this.owner = owner;
    }

    public NotificationResponse withOwner(UserOwnerResponse owner) {
        this.owner = owner;
        return this;
    }

    public List<ArticleResponse> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleResponse> articles) {
        this.articles = articles;
    }

    public NotificationResponse withArticles(List<ArticleResponse> articles) {
        this.articles = articles;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("content", content)
                .append("createdAt", createdAt)
                .append("type", type)
                .append("owner", owner)
                .append("articles", articles)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(createdAt)
                .append(id)
                .append(title)
                .append(type)
                .append(owner)
                .append(articles)
                .append(content)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NotificationResponse)) {
            return false;
        }

        NotificationResponse rhs = ((NotificationResponse) other);
        return new EqualsBuilder()
                .append(createdAt, rhs.createdAt)
                .append(id, rhs.id)
                .append(title, rhs.title)
                .append(type, rhs.type)
                .append(owner, rhs.owner)
                .append(articles, rhs.articles)
                .append(content, rhs.content)
                .isEquals();
    }
}
