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
    public RespBean addRoom(@RequestBody Room room) {
        if (!roomService.findById(room.getRoomId()).isEmpty()) {
            return RespBean.error("该宿舍信息已经存在,请勿重复添加");
        } else {
            int i = roomService.addRoom(room);
            if (i > 0) {
                return RespBean.ok("添加成功", room);
            }
            return RespBean.error("添加失败");
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
        int total = roomService.total();
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
        int i = roomService.delRoom(roomId);
        int j = roomService.delRoomInfo(roomId);
        if (i > 0 && j > 0) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    /**
     * @return com.hxx.demo.entity.HttpEntity
     * @Author Hxx
     * @Description //TODO 根据指定字段查询宿舍信息
     * @Date 11:38 2019/12/11
     * @Param [gridJson]
     **/
    @PostMapping(value = "findByKeyWords", consumes = "application/json;charset=UTF-8")
    public HttpEntity findByKeyWords(@RequestBody GridRequest gridJson) {
        HttpEntity httpEntity = new HttpEntity();
        Grid grid = new Grid();
        PageHelper.startPage(gridJson.getPageNum(),gridJson.getPageSize());
        List<Room> list = this.roomService.getGrid(gridJson);
        int total = list.size();
        grid.setData(list);
        grid.setPageNum(gridJson.getPageNum());
        grid.setTotal(total);
        grid.setPageSize(grid.getPageSize());
        httpEntity.setData(grid);
        httpEntity.setStatus(200);
        return httpEntity;
    }

    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 更新宿舍信息
     * @Date 15:49 2019/12/11
     * @Param []
     **/
    @PostMapping("updateRoom")
    public RespBean updateRoom(@RequestBody Room room) {
        int i = roomService.updateRoom(room);
        if (i > 0) {
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败");
    }

}
