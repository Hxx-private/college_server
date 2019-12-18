package com.hxx.demo.entity;

import com.google.common.collect.Lists;

import java.util.List;

public class GridRequest {
    private int pageNum;
    private int pageSize;
    private List<WhereCondition> whereConditions = Lists.newArrayList();
    private List<OrderCondition> orderConditions = Lists.newArrayList();

    public GridRequest() {
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageIndex) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageItemCount(int pageItemCount) {
        this.pageSize = pageSize;
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
