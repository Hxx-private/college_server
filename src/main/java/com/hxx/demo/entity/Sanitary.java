package com.hxx.demo.entity;
import lombok.Data;

/**
 * @author Hxx
 */
@Data

public class Sanitary {
    private String id;
    private String uName;
    private String checkTime;
    private String roomId;
    //宿舍卫生描述
    private String content;
    private Integer grade;



}
