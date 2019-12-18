package com.hxx.demo.entity;
import lombok.Data;

/**
 * @author Hxx
 */
@Data

public class Security {
    private String id;
    private int buildId;
    private String roomId;
    private String description;
    private String discover;
    private String discoverTime;
    private String operateTime;
    private String operator;
    private String checker;
    private String checkTime;
    private Integer status;
    private Integer flag;
    private Integer result;
}
