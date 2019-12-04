package com.hxx.demo.controller;

import com.github.pagehelper.PageHelper;
import com.hxx.demo.entity.Lost;
import com.hxx.demo.entity.PeriodTime;
import com.hxx.demo.entity.Result;
import com.hxx.demo.service.LostService;
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
 * @Author Hxx
 * @Description //TODO 失物信息控制层
 * @Date 12:06 2019/11/2
 * @Param
 * @return
 **/

@Api(value = "LostController")
@RestController
@RequestMapping("/api")
public class LostController{
    @Autowired
    private LostService lostService;

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 创建失物信息帖子
     * @Date 9:46 2019/10/30
     * @Param [lost]
     **/
    @ApiOperation(value = "创建失物信息帖子", notes = "根据Lost对象创建失物信息,创建人为当前登录用户，由前端来获取")
    @PostMapping("/user/addLost")
    public Map<String, Object> addLost(@RequestBody Lost lost) {
        lost.setId(IdUtils.getNumberForPK());
        lost.setCreateTime(DateUtils.getSysTime());
        int i = lostService.insertLost(lost);
        if (i==1) {
            return Result.successMap("发布成功");
        }

        return Result.failMap("发布失败");
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 查询所有丢失物品信息
     * @Date 9:47 2019/10/30
     * @Param []
     **/
    @ApiOperation(value = "查询所有丢失物品信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum", value = "当前页,默认为1", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "当前每页显示行数", dataType = "Integer")
    }
    )
    @GetMapping("/findAllLost")
    public Map<String, Object> findAllLost(Integer pageNum, Integer pageSize) {
        if (lostService.findAllLost().isEmpty()) {
            return Result.failMap("没有物品丢失");
        }
        PageHelper.startPage(pageNum, pageSize);
        return Result.successMap(lostService.findAllLost());
    }

    /**
     * @return
     * @Author Hxx
     * @Description //TODO 根据创建者查询所有失物信息
     * @Date 10:47 2019/10/31
     * @Param
     **/
    @ApiOperation(value = "根据创建者查询所有失物信息", notes = "根据creater创建者查询他所发布过的失物信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "creater", value = "创建者", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "当前页,默认为1", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "当前每页显示行数", dataType = "Integer")
    })
    @GetMapping("/findLostByCreater/{creater}")
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
    @ApiOperation(value = "查询当天所有丢失信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum", value = "当前页,默认为1", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "当前每页显示行数", dataType = "Integer")
    }
    )
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
    @ApiOperation(value = "根据指定时间段查询丢失信息", notes = "传入两个参数starttime,endtime")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "periodTime", value = "时间对象", required = true, dataType = "PeriodTime"),
            @ApiImplicitParam(name = "pageNum", value = "当前页,默认为1", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "当前每页显示行数", dataType = "Integer")
    }
    )
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
    @DeleteMapping("/sanitary/delBycreater/{creater}")
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
    @DeleteMapping("/sanitary/delBycreateTime/{createTime}")
    public Map<String, Object> delBycreateTime(@PathVariable("createTime") String createTime) {
        lostService.delBycreateTime(createTime);
        if (lostService.findBycreateTime(createTime).isEmpty()) {
            return Result.successMap("删除成功");
        }
        return Result.failMap("删除失败");
    }
}
