package com.hxx.demo.entity;

import lombok.Data;
/**
 * @author Hxx
 */
@Data
public class Menu {
    private Integer id;
    private String url;
    private String path;
    private String component;
    private String name;
    private String iconCls;
    private Byte keepAlive;
    private Byte requireAuth;
    private Byte enabled;

}
