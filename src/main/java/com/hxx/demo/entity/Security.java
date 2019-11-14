package com.hxx.demo.entity;

import lombok.Data;

/**
 * @author Hxx
 */
@Data
public class Security {
    private String id;
    private String description;
    private String discover;
    private String discoverTime;
    private String operateTime;
    private String operator;
    private Integer status;

}
