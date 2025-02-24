package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;

import java.util.ArrayList;
import java.util.List;

@AllNonnullByDefault
public final class OrderBy implements Expression {

    List<OrderRule> orderRules = new ArrayList<>();

    OrderBy() {

    }

    public static OrderBy build() {
        return new OrderBy();
    }

    public static OrderBy build(String identifier, OrderDirection direction) {
        return OrderBy.build()
            .rule(identifier, direction);
    }

    public static OrderBy build(String identifier) {
        return OrderBy.build()
            .rule(identifier, OrderDirection.ASC);
    }

    public OrderBy rule(String identifier, OrderDirection direction) {
        orderRules.add(new OrderRule(Identifier.of(identifier), direction));
        return this;
    }

    public enum OrderDirection {
        ASC, DESC
    }

    public interface OrderByConfigurer {
        void configure(OrderBy orderBy);
    }

    record OrderRule(
        Expression expression,
        OrderDirection direction
    ) {
    }
}