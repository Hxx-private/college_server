package com.hxx.demo.controller;

import com.github.pagehelper.PageHelper;
import com.hxx.demo.entity.Repair;
import com.hxx.demo.entity.Result;
import com.hxx.demo.service.RepairService;
import com.hxx.demo.utils.DateUtils;
import com.hxx.demo.utils.IdUtils;
import com.hxx.demo.utils.UserUtils;
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

@Api(value = "报修模块事务控制层")
@RestController
@RequestMapping("/api")
public class RepairController {
    @Autowired
    private RepairService repairService;

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 申请报修
     * @Date 17:19 2019/10/31
     * @Param [repair]
     **/
    @ApiOperation(value = "申请报修")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "applicant", value = "报修人由前端获取当前登录用户", required = true, dataType = "String"),
            @ApiImplicitParam(name = "room_id", value = "宿舍号为当前登录用户的宿舍号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "description", value = "报修描述", required = true, dataType = "String")
    })
    @PostMapping("user/createRepair")
    public Map<String, Object> createRepair(@RequestBody Repair repair) {
        repair.setId(IdUtils.getNumberForPK());
        repair.setApplicant(UserUtils.getCurrentUser().getName());
        repair.setRoomId(UserUtils.getCurrentUser().getRoomId());
        //默认状态为未处理
        repair.setStatus(0);
        repair.setTime(DateUtils.getSysTime());
        repairService.create(repair);
        if (null != repairService.findById(repair.getId())) {

            return Result.successMap(repair);
        }
        return Result.failMap("提交失败！");
    }

    /**
     * @return com.hxx.demo.entity.Result
     * @Author Hxx
     * @Description //TODO 处理报修信息
     * @Date 10:36 2019/11/1
     * @Param []
     **/
    @ApiOperation(value = "处理报修")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "id为当前报修信息表的id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "operator", value = "处理人 为当前登录的维修人员", required = true, dataType = "String"),
    })
    @PutMapping("user/handle")
    public Map<String, Object> handleRepair(@RequestBody Repair repair) {
        repair.setOperator(UserUtils.getCurrentUser().getName());
        repair.setStatus(1);
        repair.setEndTime(DateUtils.getSysTime());
        repairService.handleRepair(repair);
        if (repairService.findById(repair.getId()).getStatus() == 1) {
            return Result.successMap(repair);
        }
        return Result.failMap("处理失败");
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 查询所有报修信息
     * @Date 9:47 2019/10/30
     * @Param []
     **/
    @ApiOperation(value = "查询所有报修信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum", value = "当前页,默认为1", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "当前每页显示行数", dataType = "Integer")
    }
    )
    @GetMapping("/findAllRepair")
    public Map<String, Object> findAllRepair(Integer pageNum, Integer pageSize) {
        if (repairService.findAllRepair().isEmpty()) {
            return Result.failMap("没有报修信息");
        }
        PageHelper.startPage(pageNum, pageSize);
        return Result.successMap(repairService.findAllRepair());
    }

    /**
     * @return
     * @Author Hxx
     * @Description //TODO 根据报修人查询所有报修信息
     * @Date 10:47 2019/10/31
     * @Param
     **/
    @ApiOperation(value = "根据创建者查询所有报修信息", notes = "根据applicant创建者查询他所提交过的报修信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "applicant", value = "报修人", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "当前页,默认为1", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "当前每页显示行数", dataType = "Integer")
    })

    @GetMapping("/findByApplicant/{applicant}")
    public Map<String, Object> findByApplicant(@PathVariable("applicant") String applicant, Integer pageNum, Integer pageSize) {
        if (repairService.getByApplicant(UserUtils.getCurrentUser().getName()) == null) {
            Result.failMap("您还没有提交过报修信息");
        }
        PageHelper.startPage(pageNum, pageSize);
        return Result.successMap(repairService.getByApplicant(applicant));
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 查询当天所有报修信息
     * @Date 10:50 2019/10/31
     * @Param []
     **/
    @ApiOperation(value = "查询当天所有报修信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum", value = "当前页,默认为1", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "当前每页显示行数", dataType = "Integer")
    })
    @GetMapping("/getTodayRepair")
    public Map<String, Object> getTodayRepair(Integer pageNum, Integer pageSize) {
        if (repairService.getTodayRepair().isEmpty()) {
            return Result.failMap("今天没有报修信息");
        }
        PageHelper.startPage(pageNum, pageSize);
        return Result.successMap(repairService.getTodayRepair());
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 根据宿舍id删除报修信息
     * @Date 9:03 2019/11/8
     * @Param [roomId]
     **/
    @ApiOperation("根据宿舍id删除报修信息")
    @ApiImplicitParam(name = "roomId", value = "宿舍id", required = true, dataType = "String")
    @DeleteMapping("/repair/deleteRepByRid/{roomId}")
    public Map<String, Object> deleteRepByRid(@PathVariable("roomId") String roomId) {
        repairService.deleteRepByRid(roomId);
        if (repairService.findById(roomId) == null) {
            return Result.successMap("删除成功");
        }
        return Result.failMap("删除失败");
    }


}
