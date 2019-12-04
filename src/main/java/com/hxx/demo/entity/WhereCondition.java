package com.hxx.demo.entity;

import com.hxx.demo.entity.entityMenu.WhereConditionMethod;

public class WhereCondition {
    private String field;
    private WhereConditionMethod method;
    private Object value;

    public WhereCondition() {
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public WhereConditionMethod getMethod() {
        return this.method;
    }

    public void setMethod(WhereConditionMethod method) {
        this.method = method;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}