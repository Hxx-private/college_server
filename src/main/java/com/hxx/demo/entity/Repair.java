package com.hxx.demo.entity;


import lombok.Data;

/**
 * @author Hxx
 */
@Data
public class Repair {
    private String id;
    //宿舍号
    private String roomId;
    //报修描述
    private String description;
    //报修人
    private String applicant;
    //处理人
    private String operator;
    //处理状态
    private Integer status;
    //处理时间
    private String endTime;
    //报修时间
    private String time;

}
