package com.hxx.demo.entity;

import com.hxx.demo.entity.entityMenu.OrderConditionMethod;

public class OrderCondition {
    private String field;
    private OrderConditionMethod method;

    public OrderCondition() {
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public OrderConditionMethod getMethod() {
        return this.method;
    }

    public void setMethod(OrderConditionMethod method) {
        this.method = method;
    }
}