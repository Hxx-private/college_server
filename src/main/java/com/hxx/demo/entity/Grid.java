package com.hxx.demo.entity;

public class Grid {
    private int pageIndex;
    private long pageItemCount;
    private long totalCount;
    private Object data;

    public Grid() {
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getPageItemCount() {
        return this.pageItemCount;
    }

    public void setPageItemCount(long pageItemCount) {
        this.pageItemCount = pageItemCount;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}