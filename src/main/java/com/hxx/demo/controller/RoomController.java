package com.hxx.demo.controller;


import com.hxx.demo.annotation.UserLoginToken;
import com.hxx.demo.entity.Result;
import com.hxx.demo.entity.Room;
import com.hxx.demo.service.RoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Hxx
 */

@Api(value = "RoomController")
@RestController
@RequestMapping("/api")
public class RoomController {
    @Autowired
    private RoomService roomService;

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
    @PostMapping("/user/addRoom")
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
    @GetMapping("/room/findAllRommInf")
    public Map<String, Object> findAllRoom() {
        if (roomService.findAll().isEmpty()) {
            return Result.failMap("数据为空");
        }

        return Result.successMap(roomService.findAll());
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
    @DeleteMapping("/user/delRoom/{roomId}")
    public Map<String, Object> delRoom(@PathVariable("roomId") String roomId) {
        roomService.delRoom(roomId);
        if (!roomService.findById(roomId).isEmpty()) {
            return Result.failMap("删除失败");
        }
        return Result.successMap("删除成功");
    }

}
