package com.hxx.demo.controller;


import com.github.pagehelper.PageHelper;
import com.hxx.demo.entity.RespBean;
import com.hxx.demo.entity.Security;
import com.hxx.demo.service.SecurityService;
import com.hxx.demo.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hxx
 */


@Api(value = "SanitaryController")
@RestController
@RequestMapping("/security")
public class SecurityController {
    @Autowired
    private SecurityService securityService;

    @ApiOperation(value = "历史检查记录")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum", value = "当前页", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", dataType = "Integer")
    })
    @GetMapping("/findAll")
    public RespBean findAll(Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Security> list = securityService.findAll();
        int total = securityService.total();
        map.put("data", list);
        map.put("total", total);
        return RespBean.ok("", map);
    }


    @ApiOperation(value = "发布安全隐患信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "description", value = "描述", required = true, dataType = "String"),
            @ApiImplicitParam(name = "discover", value = "发布人  由前端获取当前登录用户", required = true, dataType = "String")

    })

    @PostMapping("/securityAdd")
    public RespBean securityAdd(@RequestBody Security security) {
        security.setId(IdUtils.getNumberForPK());
        security.setStatus(0);
        security.setDescription(security.getDescription());
        security.setDiscover(UserUtils.getCurrentUser().getName());
        security.setDiscoverTime(DateUtils.getSysTime());
        int i = securityService.insertSecurity(security);
        if (i>0) {
            return RespBean.ok("发布成功");
        }
    return RespBean.error("发布失败");
    }

    @ApiOperation(value = "根据宿舍id查找安全隐患信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "roomId", value = "宿舍id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "当前页", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", dataType = "Integer")
    })
    @GetMapping("/security/selectByRoomId/{roomId}")
    public List<Security> selectByRoomId(@PathVariable("roomId") String roomId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return securityService.findByRoomId(roomId);

    }

    @ApiOperation(value = "根据发现人查找安全隐患信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "discover", value = "发现人", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "当前页", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", dataType = "Integer")
    })
    @GetMapping("/security/findByDiscover/{discover}")
    public List<Security> findByDiscover(@PathVariable("discover") String discover, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        return securityService.findByDiscover(discover);

    }

    @ApiOperation(value = "根据处理状态来查找隐患信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "status", value = "处理状态  0:未处理  1：已处理", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "当前页", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", dataType = "Integer")
    })

    @GetMapping("/security/status/{status}")
    public RespBean status(@PathVariable("status") Integer status, Integer pageNum, Integer pageSize) {
        if (status == 0 || status == 1) {
            PageHelper.startPage(pageNum, pageSize);
            return RespBean.ok("查询成功", securityService.EventStatus(status));

        } else {
            return RespBean.error("状态码错误");
        }
    }


    /**
     * @return com.hxx.demo.entity.Result
     * @Author Hxx
     * @Description //TODO 根据宿舍id删除安全隐患信息
     * @Date 15:29 2019/11/7
     * @Param [userName]
     **/
    @ApiOperation("根据宿舍id来删除安全隐患信息")
    @ApiImplicitParam(name = "roomId", value = "宿舍id", required = true, dataType = "String")
    @DeleteMapping("/security/delBySeRoomId{roomId}")
    public RespBean delBySeRoomId(@PathVariable("roomId") String roomId) {
        securityService.delBySeroomId(roomId);
        if (securityService.findByRoomId(roomId) == null) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    /**
     * @return com.hxx.demo.entity.Result
     * @Author Hxx
     * @Description //TODO 根据处理时间删除宿舍安全隐患信息
     * @Date 15:30 2019/11/7
     * @Param [number]
     **/
    @ApiOperation("根据处理时间删除安全隐患信息")
    @ApiImplicitParam(name = "operateTime", value = "处理时间", required = true, dataType = "String")
    @DeleteMapping("/delByoperateTime/{operateTime}")
    public RespBean delByoperateTime(@PathVariable("operateTime") String operateTime) {
        securityService.delByoperateTime(operateTime);
        if (securityService.findByOpTime(operateTime).isEmpty()) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 处理安全隐患
     * @Date 15:54 2019/11/8
     * @Param [security]
     **/
    @ApiOperation(value = "处理安全隐患信息")
    @ApiImplicitParam(name = "operator", value = "处理人 获取当前登录用户", required = true, dataType = "String")
    @PutMapping("/security/handleSecurity")
    public RespBean handleSecurity(@RequestBody Security security) {
        securityService.findById(security.getId());
        security.setStatus(1);
        security.setOperateTime(DateUtils.getSysTime());
        securityService.handleSecurity(security);
        if (securityService.findById(security.getId()).getStatus() == 1) {
            return RespBean.ok("处理成功");
        }
        return RespBean.ok("处理失败");
    }

}
