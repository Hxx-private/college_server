package com.hxx.demo.controller;


import com.github.pagehelper.PageHelper;
import com.hxx.demo.entity.*;
import com.hxx.demo.service.RoomService;
import com.hxx.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hxx
 */

@RestController
@RequestMapping("/dor")
public class RoomController {
    @Autowired
    private RoomService roomService;
    Map<String, Object> map = new HashMap<>();
    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 添加宿舍信息
     * @Date 9:46 2019/10/30
     * @Param [lost]
     **/
    @ApiOperation(value = "添加宿舍信息", notes = "根据room对象来添加")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "roomId", value = "宿舍id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "remarks", value = "备注", required = true, dataType = "String"),
            @ApiImplicitParam(name = "num", value = "床位数", required = true, dataType = "String")
    })
    @PostMapping("/addRoom")
    public Map<String, Object> addRoom(@RequestBody Room room) {
        if (!roomService.findById(room.getRoomId()).isEmpty()) {
            return Result.failMap("该宿舍信息已经存在,请勿重复添加");
        } else {
            roomService.addRoom(room);
            if (!roomService.findById(room.getRoomId()).isEmpty()) {
                return Result.successMap(room);
            }
            return Result.failMap("添加失败");
        }


    }

    /**
     * @return
     * @Author Hxx
     * @Description //TODO 显示所有宿舍信息
     * @Date 9:09 2019/11/11
     * @Param
     **/
    @GetMapping(value = "findAllRoom")
    public RespBean findAllRoom(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Room> list = roomService.findAll();
        int total = list.size();
        map.put("data", list);
        map.put("total", total);
        return RespBean.ok("", map);
    }


    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 删除宿舍信息
     * @Date 9:46 2019/10/30
     * @Param [lost]
     **/
    @ApiOperation(value = "删除宿舍信息", notes = "根据id来删除")
    @ApiImplicitParam(name = "roomId", value = "宿舍id", required = true, dataType = "String")
    @DeleteMapping("delRoom/{roomId}")
    public RespBean delRoom(@PathVariable("roomId") String roomId) {
        roomService.delRoom(roomId);
        roomService.delRoomInfo(roomId);
        if (roomService.findById(roomId).isEmpty()) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

}
