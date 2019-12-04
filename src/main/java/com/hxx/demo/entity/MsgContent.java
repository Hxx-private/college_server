package com.hxx.demo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author Hxx
 * @Description //TODO
 * @Date 10:47 2019/11/18
 * @Param
 * @return
 **/
@Data
public class MsgContent {
    private Long id;
    private String message;
    private String title;
    private Date createDate;

}
