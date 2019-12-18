package com.hxx.demo.controller;


import com.github.pagehelper.PageHelper;
import com.hxx.demo.entity.*;
import com.hxx.demo.service.SecurityHistoryService;
import com.hxx.demo.service.SecurityService;
import com.hxx.demo.utils.DateUtils;
import com.hxx.demo.utils.IdUtils;
import com.hxx.demo.utils.UserUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hxx
 */


@RestController
@RequestMapping("/security")
public class SecurityController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private SecurityHistoryService historyService;


    /**
     * @return
     * @Author Hxx
     * @Description //TODO 根据指定字段查询隐患信息
     * @Date 11:53 2019/12/2
     * @Param
     **/
    @PostMapping(value = "sec/findByKeyWords", consumes = "application/json;charset=UTF-8")
    public HttpEntity findByKeyWords(@RequestBody GridRequest gridJson) {
        HttpEntity httpEntity = new HttpEntity();
        Grid grid = new Grid();
        PageHelper.startPage(gridJson.getPageNum(), gridJson.getPageSize());
        List<Security> list = this.securityService.getGrid(gridJson);
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
     * @Description //TODO 隐患列表
     * @Date 10:01 2019/12/17
     * @Param [pageNum, pageSize]
     **/
    @GetMapping("/sec/list")
    public RespBean findAll(Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Security> list = securityService.findAll();
        int total = securityService.total();
        map.put("data", list);
        map.put("total", total);
        return RespBean.ok("", map);
    }


    @DeleteMapping("/sec/delById/{id}")
    public RespBean delById(@PathVariable String id) {
        int i = securityService.deleteById(id);
        if (i > 0) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }


    @PostMapping("/sec/update")
    public RespBean update(@RequestBody Security security) {
        int i = securityService.update(security);
        if (i > 0) {
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败");
    }

    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 发布安全隐患信息
     * @Date 10:01 2019/12/17
     * @Param [security]
     **/
    @PostMapping("sec/add")
    public RespBean securityAdd(@RequestBody Security security) {
        security.setId(IdUtils.getNumberForPK());
        security.setStatus(0);
        security.setFlag(0);
        security.setResult(0);
        security.setDiscover(UserUtils.getCurrentUser().getName());
        security.setDiscoverTime(DateUtils.getSysTime());
        int i = securityService.insertSecurity(security);
        int j = historyService.addhistory(security);
        if (i > 0 && j > 0) {
            return RespBean.ok("发布成功");
        }
        return RespBean.error("发布失败");
    }


    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 申请复查列表
     * @Date 10:17 2019/12/17
     * @Param [pageNum, pageSize]
     **/
    @GetMapping("/sec/apply/list")
    public RespBean findApply(Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Security> list = securityService.findApply();
        List<Security> filterList = new ArrayList<>();
        for (Security security : list) {
            if (UserUtils.getCurrentUser().getRoomId().equals(security.getRoomId()) && UserUtils.getCurrentUser().getBuildId().equals(security.getBuildId())) {
                filterList.add(security);
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
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 申请复查
     * @Date 15:54 2019/11/8
     * @Param [security]
     **/
    @ApiOperation(value = "申请复查")
    @PostMapping("/sec/apply/{id}")
    public RespBean handleSecurity(@PathVariable String id) {
        Security security = new Security();
        security.setId(id);
        security.setFlag(1);
        security.setResult(1);
        security.setStatus(1);
        security.setDiscover(UserUtils.getCurrentUser().getName());
        security.setOperateTime(DateUtils.getSysTime());
        int i = securityService.handleSecurity(security);
        if (i > 0) {
            return RespBean.ok("申请成功");
        }
        return RespBean.ok("申请失败");
    }

    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 待复查列表
     * @Date 11:07 2019/12/17
     * @Param [pageNum, pageSize]
     **/
    @GetMapping("/sec/wait/list")
    public RespBean find(Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Security> list = securityService.findWait();
        int total = list.size();
        map.put("data", list);
        map.put("total", total);
        return RespBean.ok("", map);
    }


    @GetMapping("/sec/solved/list")
    public RespBean findSolved(Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Security> list = securityService.findSolved();
        int total = list.size();
        map.put("data", list);
        map.put("total", total);
        return RespBean.ok("", map);
    }


    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 历史记录
     * @Date 9:58 2019/12/17
     * @Param [pageNum, pageSize]
     **/
    @GetMapping("/history/list")
    public RespBean findhistory(Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Security> list = historyService.find();
        int total = historyService.total();
        map.put("data", list);
        map.put("total", total);
        return RespBean.ok("", map);
    }

    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 清空历史记录
     * @Date 17:05 2019/12/13
     * @Param []
     **/
    @DeleteMapping("history/delete")
    public RespBean deleteRecord() {
        int i = historyService.delete();
        if (i > 0) {
            return RespBean.ok("数据已清空");
        }
        return RespBean.error("操作失败");
    }
}
