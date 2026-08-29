
package com.synechisveltiosi.apis.app365.common.rest.response.pagination;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.domain.Page;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginationResponse {

    @JsonProperty("totalPages")
    private Integer totalPages;

    @JsonProperty("totalElements")
    private Long totalElements;

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("pageSize")
    private Integer pageSize;

    @JsonProperty("numberOfElements")
    private Integer numberOfElements;

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public PaginationResponse withTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public PaginationResponse withTotalElements(Long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public PaginationResponse withPage(Integer page) {
        this.page = page;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public PaginationResponse withPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public PaginationResponse withNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
        return this;
    }

    public static PaginationResponse from(Page page) {
        return PaginationResponseBuilder.builder()
                .withPage(page.getNumber())
                .withSize(page.getSize())
                .withNumberOfElements(page.getNumberOfElements())
                .withTotalPages(page.getTotalPages())
                .withTotalElements(page.getTotalElements())
                .build();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("totalPages", totalPages)
                .append("totalElements", totalElements)
                .append("page", page)
                .append("pageSize", pageSize)
                .append("numberOfElements", numberOfElements)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(totalPages)
                .append(pageSize)
                .append(page)
                .append(numberOfElements)
                .append(totalElements)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PaginationResponse)) {
            return false;
        }

        PaginationResponse rhs = ((PaginationResponse) other);
        return new EqualsBuilder()
                .append(totalPages, rhs.totalPages)
                .append(pageSize, rhs.pageSize)
                .append(page, rhs.page)
                .append(numberOfElements, rhs.numberOfElements)
                .append(totalElements, rhs.totalElements)
                .isEquals();
    }
}
