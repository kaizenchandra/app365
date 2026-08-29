package com.synechisveltiosi.apis.app365.common.util.domain;

import com.synechisveltiosi.apis.app365.common.util.domain.sort.DefaultSort;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultPageable {
    private Integer page = null;
    private Integer defaultPage = null;
    private Integer maxPage = Integer.MAX_VALUE;
    private Integer size = null;
    private Integer defaultSize = null;
    private Integer maxSize = 20;
    private String sortBy = null;
    private String defaultSortBy = null;
    private List<String> allowedFields;

    private DefaultPageable() {
        allowedFields = new ArrayList<>();
    }

    public static DefaultPageable builder() {
        return new DefaultPageable();
    }

    /**
     * Set the page number
     *
     * @param page
     * @return
     */
    public DefaultPageable withPage(Integer page) {
        return this.withPage(page, 0, Integer.MAX_VALUE);
    }

    /**
     * Set the page number, if the value for page is invalid, the method will try to fallback to the default value.
     *
     * @param page
     * @param defaultPage
     * @return
     */
    public DefaultPageable withPage(Integer page, Integer defaultPage) {
        return this.withPage(page, defaultPage, Integer.MAX_VALUE);
    }

    /**
     * Set the page number, if the value for page is invalid, the method will try to fallback to the default value,
     * if the default value is invalid or greater than the max value, the field will default to the max value
     *
     * @param page
     * @param defaultPage
     * @param maxPage
     * @return
     */
    public DefaultPageable withPage(Integer page, Integer defaultPage, Integer maxPage) {
        this.page = page;
        this.defaultPage = defaultPage;
        this.maxPage = maxPage;

        return this;
    }

    /**
     * Set the page size
     *
     * @param size
     * @return
     */
    public DefaultPageable withSize(Integer size) {
        return this.withSize(size, 1, 20);
    }

    /**
     * Set the page size, if the value for page size is invalid, the method will try to fallback to the default size value.
     *
     * @param size
     * @param defaultSize
     * @return
     */
    public DefaultPageable withSize(Integer size, Integer defaultSize) {
        return this.withSize(size, defaultSize, 20);
    }

    /**
     * Set the page size, if the value for page size is invalid, the method will try to fallback to the default value,
     * if the default value is invalid or greater than the max value, the field will default to the max value
     *
     * @param size
     * @param defaultSize
     * @param maxSize
     * @return
     */
    public DefaultPageable withSize(Integer size, Integer defaultSize, Integer maxSize) {
        this.size = size;
        this.defaultSize = defaultSize;
        this.maxSize = maxSize;

        return this;
    }

    /**
     * The set sorBy fields
     *
     * @param sortBy
     * @return
     */
    public DefaultPageable withSort(String sortBy) {
        return withSort(sortBy, null, new ArrayList<>());
    }

    /**
     * Set the sortBy fields and a list of allowed field to check the fields against
     *
     * @param sortBy
     * @param allowedFields
     * @return
     */
    public DefaultPageable withSort(String sortBy, List<String> allowedFields) {
        return this.withSort(sortBy, null, allowedFields);
    }

    /**
     * Set the sortBy fields and a default sort to use if sortBy value is invalid
     *
     * @param sortBy
     * @param defaultSortBy
     * @return
     */
    public DefaultPageable withSort(String sortBy, String defaultSortBy) {
        return this.withSort(sortBy, defaultSortBy, new ArrayList<>());
    }

    /**
     * Set the sort by fields and a default sort to use if the sort by value is invalid. The whole should be validated
     * against a list of allowed value
     *
     * @param sortBy
     * @param defaultSortBy
     * @param allowedFields
     * @return
     */
    public DefaultPageable withSort(String sortBy, String defaultSortBy, List<String> allowedFields) {
        this.sortBy = sortBy;
        this.defaultSortBy = defaultSortBy;
        this.allowedFields = (allowedFields == null || allowedFields.size() == 0) ? new ArrayList<>() : allowedFields;
        return this;
    }

    /**
     * Set the default value for the page
     *
     * @param defaultPage
     * @param defaultSize
     * @param defaultSortBy
     * @return
     */
    public DefaultPageable withDefaults(Integer defaultPage, Integer defaultSize, String defaultSortBy) {
        this.defaultPage = defaultPage;
        this.defaultSize = defaultSize;
        this.defaultSortBy = defaultSortBy;

        return this;
    }

    /**
     * Set the max value for the page
     *
     * @param maxPage
     * @return
     */
    public DefaultPageable withMaxPage(Integer maxPage) {
        this.maxPage = maxPage;

        return this;
    }

    /**
     * Set the max value for the size
     *
     * @param maxSize
     * @return
     */
    public DefaultPageable withMaxSize(Integer maxSize) {
        this.maxSize = maxSize;

        return this;
    }

    /**
     * Set the max value for the page
     *
     * @param maxPage
     * @param maxSize
     * @return
     */
    public DefaultPageable withMax(Integer maxPage, Integer maxSize) {
        this.maxPage = maxPage;
        this.maxSize = maxSize;

        return this;
    }

    /**
     * Set page values
     *
     * @param page
     * @param size
     * @return
     */
    public DefaultPageable with(Integer page, Integer size) {
        return this.with(page, size, null, (String[]) null);
    }

    /**
     * Set page values
     *
     * @param page
     * @param size
     * @param sortBy
     * @return
     */
    public DefaultPageable with(Integer page, Integer size, String sortBy) {
        return this.with(page, size, sortBy, (String[]) null);
    }

    /**
     * Set page values
     *
     * @param page
     * @param size
     * @param sortBy
     * @param allowedFields
     * @return
     */
    public DefaultPageable with(Integer page, Integer size, String sortBy, String[] allowedFields) {
        return this.with(page, size, sortBy,
                (allowedFields == null || allowedFields.length == 0) ? new ArrayList<>() : Arrays.asList(allowedFields));
    }

    /**
     * Set page values
     *
     * @param page
     * @param size
     * @param sortBy
     * @param allowedFields
     * @return
     */
    public DefaultPageable with(Integer page, Integer size, String sortBy, List<String> allowedFields) {
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.allowedFields = allowedFields;

        return this;
    }

    /**
     * Build a pageable
     *
     * @return
     * @throws IllegalArgumentException
     */
    public Pageable build() throws IllegalArgumentException {
        // Process information
        this.processPage();
        this.processSize();
        this.processSort();

        // Validate the page
        if (page == null || page < 0)
            throw new IllegalArgumentException("Page parameter cannot be null or less than 0");

        if (size == null || size < 1)
            throw new IllegalArgumentException("Size parameter cannot be null or less than 1");

        // Validate the sort
        Sort sort = Sort.unsorted();
        if (StringUtils.isNotEmpty(sortBy)) {
            sort = DefaultSort.of(sortBy, allowedFields);
        }

        return PageRequest.of(page, size, sort);
    }

    /**
     * Set the page number, if the value for page is invalid, the method will try to fallback to the default value,
     * if the default value is invalid or greater than the max value, the field will default to the max value
     */
    private void processPage() {
        if (page == null || page < 0) {
            if (defaultPage == null || defaultPage < 0) return;
            this.page = defaultPage;
        }

        if (maxPage == null) return;
        this.page = this.page > maxPage ? maxPage : this.page;
    }

    /**
     * Set the page size, if the value for page size is invalid, the method will try to fallback to the default value,
     * if the default value is invalid or greater than the max value, the field will default to the max value
     */
    private void processSize() {
        if (size == null || size < 0) {
            if (defaultSize == null || defaultSize < 0) return;
            this.size = defaultSize;
        }

        if (maxSize == null) return;
        this.size = this.size > maxSize ? maxSize : this.size;
    }

    /**
     * Set the sort, if the sort value is empty, make a fallback to the default sort value
     */
    private void processSort() {
        if (StringUtils.isEmpty(sortBy)) {
            if (StringUtils.isNotEmpty(defaultSortBy)) {
                sortBy = defaultSortBy;
            }
        }
    }
}
