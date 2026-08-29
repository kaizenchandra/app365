
package com.synechisveltiosi.apis.app365.common.dto.comments;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentRequest {

    @JsonProperty("content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommentRequest withContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("content", content)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(content)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CommentRequest)) {
            return false;
        }

        CommentRequest rhs = ((CommentRequest) other);
        return new EqualsBuilder()
                .append(content, rhs.content)
                .isEquals();
    }
}
