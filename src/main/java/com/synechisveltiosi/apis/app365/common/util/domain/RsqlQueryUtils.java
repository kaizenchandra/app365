package com.synechisveltiosi.apis.app365.common.util.domain;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.Node;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RsqlQueryUtils {

    /**
     * Assert that the fields been used in the query are allowed to be use in this context
     *
     * @param rootNode
     * @param allowedFields
     * @throws IllegalArgumentException
     */
    public static void assertAllowedFields(Node rootNode, List<String> allowedFields) throws IllegalArgumentException {
        Assert.notNull(allowedFields, "Allowed fields cannot be null.");

        // Break the process if allowed fields is empty
        if (allowedFields.isEmpty()) return;

        // Get all the fields from the node
        Set<String> queryFields = RsqlQueryUtils.processUnAllowedFields(rootNode, new HashSet<>());

        // No result, then nothing to validate
        if (queryFields.isEmpty()) return;

        // Get all unknown fields
        List<String> unknownFields = queryFields.stream()
                .filter(field -> !allowedFields.contains(field))
                .collect(Collectors.toList());

        // If there are invalid fields, throw exception
        if (!unknownFields.isEmpty())
            throw new IllegalArgumentException(
                    String.format("Unable to search with these fields: %s. Try the following fields instead: %s.",
                            StringUtils.join(unknownFields, ", "),
                            StringUtils.join(allowedFields, ", ")));
    }

    /**
     * Get all the fields in the nodes
     *
     * @param node
     * @param fields
     * @return
     */
    public static Set<String> processUnAllowedFields(Node node, Set<String> fields) {
        // Validate comparison nodes
        if (node instanceof ComparisonNode) {
            fields.add(((ComparisonNode) node).getSelector());
        } else if (node instanceof LogicalNode) { // Validate logical fields
            for (Node newNode : ((LogicalNode) node).getChildren()) {
                processUnAllowedFields(newNode, fields);
            }
        } else {
            throw new IllegalArgumentException("Unknown expression type: " + node.getClass());
        }

        return fields;
    }
}
