
package com.synechisveltiosi.apis.app365.news.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("coverPicture")
    private String coverPicture;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArticleResponse withId(String id) {
        this.id = id;
        return this;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public ArticleResponse withCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("coverPicture", coverPicture)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(coverPicture)
                .append(id)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ArticleResponse)) {
            return false;
        }

        ArticleResponse rhs = ((ArticleResponse) other);
        return new EqualsBuilder()
                .append(coverPicture, rhs.coverPicture)
                .append(id, rhs.id)
                .isEquals();
    }
}
