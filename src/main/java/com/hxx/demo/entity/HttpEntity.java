package com.hxx.demo.entity;

import lombok.Data;

import java.io.Serializable;
/**
 * @author Hxx
 */
@Data
public class HttpEntity<T> implements Serializable {
    private String message = "";
    private T data = null;
    private int status= 200;
}
