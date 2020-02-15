package com.hxx.demo.controller;


import com.github.pagehelper.PageHelper;
import com.hxx.demo.entity.*;
import com.hxx.demo.service.RoomService;
import com.hxx.demo.utils.DateUtils;
import com.hxx.demo.utils.PoiUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/addRoom")
    public RespBean addRoom(@RequestBody Room room) {
        Integer buildId=room.getBuildId();
        String roomId=room.getRoomId();
        if (roomService.findById(buildId, roomId).isEmpty()) {
            room.setCreateTime(DateUtils.getSysTime());
            int i = roomService.addRoom(room);
            if (roomService.findByBuildid(buildId)==null){
              roomService.addBuild(buildId);
            }
            int k = roomService.addInfo(buildId,roomId);
            if (i > 0 && k > 0) {
                return RespBean.ok("添加成功");
            }
            return RespBean.error("添加失败");

        }

        return RespBean.error("该宿舍信息已经存在,请勿重复添加");
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
        PageHelper.startPage(gridJson.getPageNum(), gridJson.getPageSize());
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
    @PostMapping("/updateRoom")
    public RespBean updateRoom(@RequestBody Room room) {
        long id = roomService.findInfo(room.getBuildId(), room.getRoomId()).getId();
        Room room1 = new Room();
        room1.setId(id);
        room1.setRoomId(room.getRoomId());
        room1.setBuildId(room.getBuildId());
        int j = roomService.editRoomInfo(room1);
        int i = roomService.editRoom(room);
        if (i > 0 && j > 0) {
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败");
    }


    /**
     * @return org.springframework.http.ResponseEntity<byte [ ]>
     * @Author Hxx
     * @Description //TODO 导出宿舍信息
     * @Date 10:46 2019/12/12
     * @Param []
     **/
    @GetMapping(value = "/export")
    public ResponseEntity<byte[]> exportUser() {
        return PoiUtils.exportDormExcel(roomService.findAll());
    }

    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 批量删除
     * @Date 16:06 2019/12/27
     * @Param [ids]
     **/
    @DeleteMapping("deleteBatch/{ids}")
    public RespBean deleteBatch(@PathVariable String ids) {
        if (roomService.deleteBatch(ids)) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO  根据roomId查找宿舍信息
     * @Date 14:41 2019/12/28
     * @Param [buildId, roomId]
     **/
    @GetMapping("/findById")
    public RespBean findById(Integer buildId, String roomId) {
        List<Room> list = roomService.findById(buildId, roomId);
        if (list.size() > 0) {
            return RespBean.ok("", list);
        }
        return RespBean.error("查询失败");

    }


}
