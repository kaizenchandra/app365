package com.synechisveltiosi.apis.app365.common.rest.response.pagination;

import java.util.List;

public class PageResponseBuilder<T> {

    private List<T> data;
    private PaginationResponse pagination;

    private PageResponseBuilder() {

    }

    public static <T> PageResponseBuilder<T> builder() {
        return new PageResponseBuilder<T>();
    }

    public PageResponseBuilder<T> withData(List<T> data) {
        this.data = data;
        return this;
    }

    public PageResponseBuilder<T> withPagination(PaginationResponse pagination) {
        this.pagination = pagination;
        return this;
    }

    public PageResponse<T> build() {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setData(data);
        pageResponse.setPagination(pagination);
        return pageResponse;
    }
}
