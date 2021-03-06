package com.hxx.demo.entity;

import lombok.Data;

/**
 * @author Martin
 * @description 时间段
 * @date 2019/10/30
 */
@Data
public class PeriodTime {
    private Integer pageNum;
    private Integer pageSize;
    private String startTime;
    private String endTime;
}
