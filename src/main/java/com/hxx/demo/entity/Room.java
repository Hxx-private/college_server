package com.hxx.demo.entity;
import lombok.Data;

import java.util.Date;

/**
 * @author Hxx
 */
@Data
public class Room {
    private long id;

    private String roomId;
    //床位数
    public  Integer num;
   //备注
    private String remarks;

    private Integer buildId;

    private Integer bNum;

    private String createTime;

}
