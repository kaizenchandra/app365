package com.synechisveltiosi.apis.app365.common.rest.response.pagination;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class PageResponse<T> {
    private List<T> data;
    private PaginationResponse pagination;

    public PageResponse() {
        this(null);
    }

    public PageResponse(List<T> data) {
        this(data, null);
    }

    public PageResponse(List<T> data, PaginationResponse pagination) {
        this.data = data;
        this.pagination = pagination;
    }

    public List<T> getData() {
        return data;
    }

    public PaginationResponse getPagination() {
        return pagination;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setPagination(PaginationResponse pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("data", data)
                .append("pagination", pagination)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        PageResponse<?> that = (PageResponse<?>) other;

        if (getData() != null
                ? (!getData().containsAll(that.getData()) || !that.getData().containsAll(getData()))
                : that.getData() != null)
            return false;

        return getPagination() != null ? getPagination().equals(that.getPagination()) : that.getPagination() == null;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(data)
                .append(pagination)
                .hashCode();
    }
}
