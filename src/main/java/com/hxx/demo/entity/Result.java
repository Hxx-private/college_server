package com.hxx.demo.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hxx
 */
@Data
public class Result {
    public boolean success;
    public String message;
    public Object data;
    public int code;


    public static Result success() {
        Result res = new Result();
        res.setSuccess(true);
        res.setCode(200);
        res.setMessage("success");
        return res;
    }

    public static Result success(Object data) {
        Result res = new Result();
        res.setSuccess(true);
        res.setCode(200);
        res.setMessage("success");
        res.setData(data);
        return res;
    }

    public static Result fail() {
        Result res = new Result();
        res.setSuccess(false);
        res.setCode(403);
        res.setMessage("fail");
        return res;
    }

    public static Result fail(Object data) {
        Result res = new Result();
        res.setSuccess(false);
        res.setCode(403);
        res.setMessage("fail");
        res.setData(data);
        return res;
    }

    public static Map<String, Object> successMap(Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("success", true);
        map.put("message", "success");
        map.put("data", data);
        return map;
    }

    public static Map<String, Object> failMap(Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 500);
        map.put("success", false);
        map.put("message", "fail");
        map.put("data", data);
        return map;
    }
}
