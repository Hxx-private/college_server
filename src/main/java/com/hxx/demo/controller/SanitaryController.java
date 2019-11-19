package com.hxx.demo.controller;

import com.github.pagehelper.PageHelper;
import com.hxx.demo.entity.Result;
import com.hxx.demo.entity.Sanitary;
import com.hxx.demo.service.SanitaryService;
import com.hxx.demo.utils.DateUtils;
import com.hxx.demo.utils.IdUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Martin
 * @description 卫生相关 Controller
 * @date 2019/10/31
 */

@Api(value = "SanitaryController")
@RestController
@RequestMapping("/api")
public class SanitaryController {
    @Autowired
    SanitaryService sanitaryService;

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 卫生信息反馈
     * @Date 13:33 2019/11/2
     * @Param [sanitary]
     **/
    @ApiOperation("卫生检查信息反馈")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "roomId", value = "宿舍id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "content", value = "反馈内容", required = true, dataType = "String"),
            @ApiImplicitParam(name = "grade", value = "分数", required = true, dataType = "String")
    })
    @PostMapping("/sanitary/add")
    public Map<String, Object> sanitaryAdd(@RequestBody Sanitary sanitary) {
        sanitary.setCheckTime(DateUtils.getSysTime());
        sanitary.setId(IdUtils.getNumberForPK());
        sanitaryService.addSanitary(sanitary);
        if (!sanitaryService.findById(sanitary.getId()).isEmpty()) {

            return Result.successMap(sanitary);
        }
        return Result.failMap("添加失败");

    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 显示所有检查信息
     * @Date 13:33 2019/11/2
     * @Param []
     **/
    @ApiOperation("显示所有检查信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum", value = "当前页", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", dataType = "Integer")
    })
    @GetMapping("/sanitary/findAll")
    public Map<String, Object> sanitaryFindAll(Integer pageNum, Integer pageSize) {
        if (!sanitaryService.selectAllSanitary().isEmpty()) {
            //pageNum：当前页数   pageSize：当前页需要显示的数量
            PageHelper.startPage(pageNum, pageSize);
            return Result.successMap(sanitaryService.selectAllSanitary());
        }
        return Result.failMap("查询失败,请稍后查询");
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 根据宿舍id查询该宿舍的卫生状况
     * @Date 13:52 2019/11/2
     * @Param [roomId]
     **/
    @ApiOperation("根据宿舍id查询该宿舍的卫生状况")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "roomId", value = "宿舍id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "当前页", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", dataType = "Integer")
    })
    @GetMapping("/sanitary/findBySaRoomId/{room_id}")
    public Map<String, Object> findBySaRoomId(@PathVariable("room_id") String roomId, Integer pageNum, Integer pageSize) {
        if (!sanitaryService.findBySaRoomid(roomId).isEmpty()) {
            //pageNum：当前页数   pageSize：当前页需要显示的数量
            PageHelper.startPage(pageNum, pageSize);
            return Result.successMap(sanitaryService.findBySaRoomid(roomId));
        }
        return Result.failMap("查询失败,请稍后查询");
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 根据指定时间查询指定宿舍的卫生信息
     * @Date 14:31 2019/11/2
     * @Param [sanitary]
     **/
    @ApiOperation("根据指定时间查询指定宿舍的卫生信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "checkTime", value = "检查时间", required = true, dataType = "String"),
            @ApiImplicitParam(name = "roomId", value = "宿舍id", required = true, dataType = "String")
    })
    @GetMapping("/sanitary/findRidByCtm")
    public Map<String, Object> findRidByCtm(@RequestBody Sanitary sanitary) {
        if (!sanitaryService.findRidByCtm(sanitary.getRoomId(), sanitary.getCheckTime()).isEmpty()) {
            return Result.successMap(sanitaryService.findRidByCtm(sanitary.getRoomId(), sanitary.getCheckTime()));
        }
        return Result.failMap("查询失败,请稍后查询");
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 根据宿舍id删除卫生检查信息
     * @Date 9:03 2019/11/8
     * @Param [roomId]
     **/
    @ApiOperation("根据宿舍id删除卫生检查信息")
    @ApiImplicitParam(name = "roomId", value = "宿舍id", required = true, dataType = "String")
    @DeleteMapping("/sanitary/delBySaRoomId/{roomId}")
    public Map<String, Object> delByRoomId(@PathVariable("roomId") String roomId) {
        sanitaryService.delBySaroomId(roomId);
        if (sanitaryService.findBySaRoomid(roomId).isEmpty()) {
            return Result.successMap("删除成功");
        }
        return Result.failMap("删除失败");
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 根据检查时间删除卫生信息
     * @Date 16:07 2019/11/8
     * @Param [checkTime]
     **/
    @ApiOperation("根据检查时间删除卫生信息")
    @ApiImplicitParam(name = "checkTime", value = "检查时间", required = true, dataType = "String")
    @DeleteMapping("/sanitary/delByCheckTime")
    public Map<String, Object> delByCheckTime( String checkTime) {
        sanitaryService.delBycheckTime(checkTime);
        if (sanitaryService.findBySaCheckTime(checkTime).isEmpty()) {
            return Result.successMap("删除成功");
        }
        return Result.failMap("删除失败");
    }
}
