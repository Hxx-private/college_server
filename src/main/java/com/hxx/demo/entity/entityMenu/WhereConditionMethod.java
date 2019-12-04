package com.hxx.demo.entity.entityMenu;

public enum WhereConditionMethod {
    Equal,
    NotEqual,
    Like,
    LeftLike,
    RightLike,
    In,
    NotIn,
    LessThan,
    GreaterThan,
    LessThanEqual,
    GreaterThanEqual,
    IsNull,
    NotNull;

    private WhereConditionMethod() {
    }
}