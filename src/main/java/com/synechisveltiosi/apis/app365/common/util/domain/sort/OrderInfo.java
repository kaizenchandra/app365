package com.synechisveltiosi.apis.app365.common.util.domain.sort;

import org.springframework.data.domain.Sort;

class OrderInfo {

    private String field;
    private Sort.Direction direction;

    OrderInfo(String field) {
        this(field, Sort.Direction.ASC);
    }

    public OrderInfo(String field, Sort.Direction direction) {
        setField(field);
        setDirection(direction);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Sort.Direction getDirection() {
        return direction;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }
}
