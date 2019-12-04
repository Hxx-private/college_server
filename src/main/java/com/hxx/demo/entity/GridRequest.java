package com.hxx.demo.entity;

import com.google.common.collect.Lists;

import java.util.List;

public class GridRequest {
    private int pageIndex;
    private int pageItemCount;
    private List<WhereCondition> whereConditions = Lists.newArrayList();
    private List<OrderCondition> orderConditions = Lists.newArrayList();

    public GridRequest() {
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageItemCount() {
        return this.pageItemCount;
    }

    public void setPageItemCount(int pageItemCount) {
        this.pageItemCount = pageItemCount;
    }

    public List<WhereCondition> getWhereConditions() {
        return this.whereConditions;
    }

    public void setWhereConditions(List<WhereCondition> whereConditions) {
        this.whereConditions = whereConditions;
    }

    public List<OrderCondition> getOrderConditions() {
        return this.orderConditions;
    }

    public void setOrderConditions(List<OrderCondition> orderConditions) {
        this.orderConditions = orderConditions;
    }
}
