package com.hxx.demo.controller;

import com.github.pagehelper.PageHelper;
import com.hxx.demo.entity.*;
import com.hxx.demo.service.SanitaryService;
import com.hxx.demo.utils.DateUtils;
import com.hxx.demo.utils.IdUtils;
import com.hxx.demo.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Martin
 * @description 卫生相关 Controller
 * @date 2019/10/31
 */

@Api(value = "SanitaryController")
@RestController
@RequestMapping("/sanitary")
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
    @PostMapping("/add")
    public RespBean sanitaryAdd(@RequestBody Sanitary sanitary) {
        sanitary.setCheckTime(DateUtils.getSysTime());
        sanitary.setId(IdUtils.getNumberForPK());
        sanitary.setUName(UserUtils.getCurrentUser().getName());
        int i = sanitaryService.addSanitary(sanitary);
        if (i > 0) {

            return RespBean.ok("添加成功");
        }
        return RespBean.ok("添加失败");

    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 显示所有检查信息
     * @Date 13:33 2019/11/2
     * @Param []
     **/
    @GetMapping("/findAll")
    public RespBean sanitaryFindAll(Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Sanitary> list = sanitaryService.selectAllSanitary();
        int total = sanitaryService.total();
        map.put("data", list);
        map.put("total", total);
        return RespBean.ok("", map);
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
    @DeleteMapping("delBySaRoomId/{roomId}")
    public RespBean delByRoomId(@PathVariable("roomId") String roomId) {
        int i = sanitaryService.delBySaroomId(roomId);
        if (i > 0) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }


    @PostMapping("/update")
    public RespBean update(@RequestBody Sanitary sanitary) {
        int update = sanitaryService.update(sanitary);

        if (update > 0) {
            return RespBean.ok("保存成功");
        }

        return RespBean.error("保存失败");

    }

    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 检查记录
     * @Date 9:37 2019/12/19
     * @Param [pageNum, pageSize]
     **/
    @GetMapping("dor/check/list")
    public RespBean findList(Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();

        List<Sanitary> sanitarys = sanitaryService.selectAllSanitary();
        List<Sanitary> filterList = new ArrayList<>();
        int num=0;
        for (Sanitary sanitary : sanitarys) {
            if (UserUtils.getCurrentUser().getRoomId().equals(sanitary.getRoomId()) && UserUtils.getCurrentUser().getBuildId().equals(sanitary.getBuildId())) {
                PageHelper.startPage(pageNum, pageSize);
                filterList.add(sanitary);
                num++;
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Sanitary> list = sanitaryService.selectAllSanitary();
        List<Sanitary> filterLists = new ArrayList<>();
        for (Sanitary sanitary : list) {
            if (UserUtils.getCurrentUser().getRoomId().equals(sanitary.getRoomId()) && UserUtils.getCurrentUser().getBuildId().equals(sanitary.getBuildId())) {
                PageHelper.startPage(pageNum, pageSize);
                filterLists.add(sanitary);
            }
        }

        if (filterLists.size() > 0) {
            int total = filterLists.size();
            map.put("data", filterLists);
            map.put("total", total);
            return RespBean.ok("", map);
        }
        return RespBean.ok("暂无数据");

    }

    /**
     * @Author Hxx
     * @Description //TODO 根据指定字段查询卫生信息
     * @Date 11:53 2019/12/2
     * @Param
     * @return
     **/
    @PostMapping(value = "san/findByKeyWords", consumes = "application/json;charset=UTF-8")
    public HttpEntity findByKeyWords(@RequestBody GridRequest gridJson) {
        HttpEntity httpEntity = new HttpEntity();
        Grid grid = new Grid();
        PageHelper.startPage(gridJson.getPageNum(),gridJson.getPageSize());
        List<Sanitary> list = this.sanitaryService.getGrid(gridJson);
        int total = list.size();
        grid.setData(list);
        grid.setPageNum(gridJson.getPageNum());
        grid.setTotal(total);
        grid.setPageSize(gridJson.getPageSize());
        httpEntity.setData(grid);
        httpEntity.setStatus(200);
        return httpEntity;
    }
}
