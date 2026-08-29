package com.synechisveltiosi.apis.app365.common.util.domain.sort;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultSort {

    private static final String SORT_FIELD_REGEX_PATTERN = "^[\\+\\-][a-zA-Z_][a-zA-Z_0-9]*$";

    public static Sort of(String fields) {
        return DefaultSort.of(fields, (String[]) null);
    }

    public static Sort of(String fields, String[] allowedSortFields) {
        return DefaultSort.of(fields,
                (allowedSortFields == null || allowedSortFields.length == 0)
                        ? new ArrayList<>() : Arrays.asList(allowedSortFields));
    }

    public static Sort of(String fields, List<String> allowedFields) {
        if (fields == null || "".equals(fields.trim())) {
            throw new IllegalArgumentException("At least a field should be provided to sort on.");
        }

        List<Sort.Order> orders = new ArrayList<>();
        String[] fieldArr = fields.split(",");
        for (String field : fieldArr) {
            OrderInfo orderInfo = processOrder(field, allowedFields);
            orders.add(new Sort.Order(orderInfo.getDirection(), orderInfo.getField().trim()));
        }

        return Sort.by(orders);
    }

    public static <T> List<Order> from(Sort sort, CriteriaBuilder builder, Root<T> root) throws IllegalArgumentException {
        if (sort == null)
            throw new IllegalArgumentException("Sort cannot be null.");

        return sort.stream()
                .map(order -> {
                    if (order.getDirection() == Sort.Direction.ASC) return builder.asc(root.get(order.getProperty()));
                    return builder.desc(root.get(order.getProperty()));
                })
                .collect(Collectors.toList());
    }

    private static OrderInfo processOrder(String field, List<String> allowedFields) {
        // Valid the field is not empty
        if (field == null || "".equals(field.trim())) {
            throw new IllegalArgumentException("The field to sort on cannot be null or empty.");
        }

        // If no sign is set for the field, it is assumed to be ascending
        if (field.charAt(0) != '+' && field.charAt(0) != '-') {
            field = "+" + field;
        }

        // Validate the variable is safes
        if (!field.matches(SORT_FIELD_REGEX_PATTERN)) {
            throw new IllegalArgumentException("The field name is not a valid variable name.");
        }

        char sign = field.charAt(0);
        String property = field.substring(1);

        // Check if this field is allowed
        if (allowedFields.size() > 0 && !allowedFields.contains(property.trim())) {
            throw new IllegalArgumentException(
                    String.format("Unrecognized field '%s' to sort on. Try one of the following: %s",
                            property, StringUtils.join(allowedFields, ", ")));
        }

        return new OrderInfo(property, sign == '+' ? Sort.Direction.ASC : Sort.Direction.DESC);
    }
}
