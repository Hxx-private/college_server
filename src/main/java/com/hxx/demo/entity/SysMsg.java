package com.hxx.demo.entity;

import lombok.Data;
/**
 * @Author Hxx
 * @Description //TODO
 * @Date 10:47 2019/11/18
 * @Param
 * @return
 **/
@Data
public class SysMsg {
    private Long id;
    private Long mid;
    private Integer type;
    private Long userId;
    private Integer state;
    private MsgContent msgContent;


}
