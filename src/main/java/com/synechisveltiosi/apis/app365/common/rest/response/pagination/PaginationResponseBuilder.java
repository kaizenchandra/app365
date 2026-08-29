package com.synechisveltiosi.apis.app365.common.rest.response.pagination;

public class PaginationResponseBuilder {

    private PaginationResponse paginationResponse;

    private PaginationResponseBuilder() {
        paginationResponse = new PaginationResponse();
    }

    public static PaginationResponseBuilder builder() {
        return new PaginationResponseBuilder();
    }

    public PaginationResponseBuilder withPage(int page) {
        paginationResponse.setPage(page);
        return this;
    }

    public PaginationResponseBuilder withSize(int size) {
        paginationResponse.setPageSize(size);
        return this;
    }

    public PaginationResponseBuilder withNumberOfElements(int numberOfElements) {
        paginationResponse.setNumberOfElements(numberOfElements);
        return this;
    }

    public PaginationResponseBuilder withTotalPages(int totalPages) {
        paginationResponse.setTotalPages(totalPages);
        return this;
    }

    public PaginationResponseBuilder withTotalElements(long totalElements) {
        paginationResponse.setTotalElements(totalElements);
        return this;
    }

    public PaginationResponse build() {
        return paginationResponse;
    }
}
