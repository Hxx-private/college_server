package com.hxx.demo.controller;

import com.github.pagehelper.PageHelper;
import com.hxx.demo.entity.*;
import com.hxx.demo.service.LostService;
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
 * @Author Hxx
 * @Description //TODO 失物信息控制层
 * @Date 12:06 2019/11/2
 * @Param
 * @return
 **/

@Api(value = "LostController")
@RestController
@RequestMapping("/lost")
public class LostController {
    @Autowired
    private LostService lostService;
    Map<String, Object> map = new HashMap<>();

    /**
     * @return
     * @Author Hxx
     * @Description //TODO 根据创建者查询所有失物信息
     * @Date 10:47 2019/10/31
     * @Param
     **/
    @GetMapping("/los/findLostByCreater/{creater}")
    public Map<String, Object> findLostByCreater(@PathVariable("creater") String creater, Integer pageNum, Integer pageSize) {
        if (lostService.getLostByCreater(creater).isEmpty()) {
            Result.failMap("您还没有发布过失物信息");
        }
        PageHelper.startPage(pageNum, pageSize);
        return Result.successMap(lostService.getLostByCreater(creater));
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 查询当天所有丢失信息
     * @Date 10:50 2019/10/31
     * @Param []
     **/
    @GetMapping("/getTodayAllLost")
    public Map<String, Object> getTodayAllLost(Integer pageNum, Integer pageSize) {
        if (lostService.getTodayAllLost().isEmpty()) {
            return Result.failMap("今天没有丢失物品信息");
        }
        //pageNum：当前页数   pageSize：当前页需要显示的数量
        PageHelper.startPage(pageNum, pageSize);
        return Result.successMap(lostService.getTodayAllLost());
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 根据指定时间段查询丢失信息
     * @Date 11:15 2019/10/31
     * @Param [periodTime]
     **/
    @GetMapping("/getLostByPeriod")
    public Map<String, Object> getLostByPeriod(@RequestBody PeriodTime periodTime, Integer pageNum, Integer pageSize) {
        if (lostService.getLostByPeriod(periodTime).isEmpty()) {
            return Result.failMap("所选时间段内没有丢失物品信息");
        }
        //pageNum：当前页数   pageSize：当前页需要显示的数量
        PageHelper.startPage(pageNum, pageSize);
        return Result.successMap(lostService.getLostByPeriod(periodTime));
    }


    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 根据发布人来删除帖子
     * @Date 9:03 2019/11/8
     * @Param [roomId]
     **/
    @ApiOperation("根据发布人来删除帖子")
    @ApiImplicitParam(name = "creater", value = "创建人", required = true, dataType = "String")
    @DeleteMapping("delBycreater/{creater}")
    public Map<String, Object> delBycreater(@PathVariable("creater") String creater) {
        lostService.delBycreater(creater);
        if (lostService.getLostByCreater(creater).isEmpty()) {
            return Result.successMap("删除成功");
        }
        return Result.failMap("删除失败");
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 根据发布时间来删除帖子
     * @Date 16:07 2019/11/8
     * @Param [checkTime]
     **/
    @ApiOperation("根据发布时间来删除帖子")
    @ApiImplicitParam(name = "createTime", value = "发布人", required = true, dataType = "String")
    @DeleteMapping("/delBycreateTime/{createTime}")
    public Map<String, Object> delBycreateTime(@PathVariable("createTime") String createTime) {
        lostService.delBycreateTime(createTime);
        if (lostService.findBycreateTime(createTime).isEmpty()) {
            return Result.successMap("删除成功");
        }
        return Result.failMap("删除失败");
    }

    /**
     * @return com.hxx.demo.entity.HttpEntity
     * @Author Hxx
     * @Description //TODO 根据指定字段查询失物信息
     * @Date 10:06 2019/12/12
     * @Param [gridJson]
     **/
    @PostMapping(value = "findByKeyWords", consumes = "application/json;charset=UTF-8")
    public HttpEntity findByKeyWords(@RequestBody GridRequest gridJson) {
        HttpEntity httpEntity = new HttpEntity();
        Grid grid = new Grid();
        PageHelper.startPage(gridJson.getPageNum(), gridJson.getPageSize());
        List<Lost> list = this.lostService.getGrid(gridJson);
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
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 创建失物信息帖子
     * @Date 9:46 2019/10/30
     * @Param [lost]
     **/
    @ApiOperation(value = "创建失物信息帖子", notes = "根据Lost对象创建失物信息,创建人为当前登录用户，由前端来获取")
    @PostMapping("/los/addLost")
    public RespBean addLost(@RequestBody Lost lost) {
        lost.setId(IdUtils.getNumberForPK());
        lost.setCreateTime(DateUtils.getSysTime());
        int i = lostService.insertLost(lost);
        if (i == 1) {
            return RespBean.ok("发布成功", lost);
        }

        return RespBean.error("发布失败", lost);
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 失物信息中心
     * @Date 9:47 2019/10/30
     * @Param []
     **/
    @GetMapping("/los/findAllLost")
    public RespBean findAllLost(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Lost> list = lostService.findAllLost();
        int total = lostService.total();
        map.put("data", list);
        map.put("total", total);
        return RespBean.ok("数据获取成功", map);
    }

    /**
     * 功能描述//TODO 发布记录
     *
     * @param pageNum
     * @param pageSize
     * @return com.hxx.demo.entity.RespBean
     * @author Mzx
     * @date 2019/12/17
     */
    @GetMapping("/los/findAllHistoryLost")
    public RespBean findAllHistoryLost(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Lost> list = lostService.findAllHistoryLost();
        List<Lost> filterList = new ArrayList<>();
        for (Lost lost : list) {
            if (UserUtils.getCurrentUser().getName().equals(lost.getCreater())) {
                filterList.add(lost);
            }
        }

        if (filterList.size() > 0) {
            int total = filterList.size();
            map.put("data", filterList);
            map.put("total", total);
            return RespBean.ok("", map);
        }
        return RespBean.ok("暂无数据");

    }


    /**
     * 功能描述//TODO一键删除历史记录表所哟的记录
     *
     * @param
     * @return com.hxx.demo.entity.RespBean
     * @author Mzx
     * @date 2019/12/17
     */

    @DeleteMapping("/los/deleteAllHistory")
    public RespBean deleteAll() {
        if (lostService.deleteAll() != 0) {
            return RespBean.ok("数据已经清空");
        }
        return RespBean.error("操作失败");
    }
}
