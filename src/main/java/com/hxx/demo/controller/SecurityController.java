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
        int total = securityService.findAll().size();
        PageHelper.startPage(pageNum, pageSize);
        List<Security> list = securityService.findAll();
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
        security.setOperator("");
        security.setChecker("");
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
        String roomId = UserUtils.getCurrentUser().getRoomId();
        Integer buildId = UserUtils.getCurrentUser().getBuildId();
        int total = securityService.findApply(buildId, roomId).size();
        PageHelper.startPage(pageNum, pageSize);
        List<Security> list = securityService.findApply(buildId, roomId);
        if (list.size() > 0) {
            map.put("data", list);
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
        security.setStatus(1);
        security.setFlag(1);
        security.setResult(1);
        security.setOperator(UserUtils.getCurrentUser().getName());
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
        int total = securityService.waitTotal();
        PageHelper.startPage(pageNum, pageSize);
        List<Security> list = securityService.findWait();
        map.put("data", list);
        map.put("total", total);
        if (list.size() > 0) {
            return RespBean.ok("", map);
        }
        return RespBean.ok("暂无数据", map);
    }

    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 处理复查  合格
     * @Date 11:43 2019/12/18
     * @Param [id]
     **/
    @PostMapping("/sec/waitOk/{id}")
    public RespBean handleWaitOk(@PathVariable String id) {
        Security security = new Security();
        security.setId(id);
        security.setStatus(1);
        security.setFlag(1);
        security.setResult(2);
        security.setChecker(UserUtils.getCurrentUser().getName());
        security.setCheckTime(DateUtils.getSysTime());
        int i = securityService.handleWait(security);
        Security security1 = securityService.findById(id);
        security1.setId(IdUtils.getNumberForPK());
        historyService.addhistory(security1);
        if (i > 0) {
            return RespBean.ok("操作成功");
        }
        return RespBean.ok("操作失败");
    }

    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 处理复查  不合格
     * @Date 11:43 2019/12/18
     * @Param [id]
     **/
    @PostMapping("/sec/waitError/{id}")
    public RespBean handleWaitError(@PathVariable String id) {
        Security security = new Security();
        security.setId(id);
        security.setStatus(0);
        security.setFlag(0);
        security.setResult(3);
        security.setChecker(UserUtils.getCurrentUser().getName());
        security.setCheckTime(DateUtils.getSysTime());
        int i = securityService.handleWait(security);
        Security security1 = securityService.findById(id);
        security1.setId(IdUtils.getNumberForPK());
        historyService.addhistory(security1);
        if (i > 0) {
            return RespBean.ok("操作成功");
        }
        return RespBean.ok("操作失败");
    }

    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 已处理列表
     * @Date 11:33 2019/12/18
     * @Param [pageNum, pageSize]
     **/
    @GetMapping("/sec/solved/list")
    public RespBean findSolved(Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Security> list = securityService.findSolved();
        int total = securityService.solvedTotal();
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
    @GetMapping("/sec/history/list")
    public RespBean findhistory(Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        int total = historyService.total();
        PageHelper.startPage(pageNum, pageSize);
        List<Security> list = historyService.find();
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
    @DeleteMapping("/sec/history/delete")
    public RespBean deleteRecord() {
        int i = historyService.delete();
        if (i > 0) {
            return RespBean.ok("数据已清空");
        }
        return RespBean.error("操作失败");
    }

    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 检查记录
     * @Date 10:17 2019/12/17
     * @Param [pageNum, pageSize]
     **/
    @GetMapping("/sec/check/list")
    public RespBean check(Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        String roomId = UserUtils.getCurrentUser().getRoomId();
        Integer buildId = UserUtils.getCurrentUser().getBuildId();
        int total = historyService.findCheck(buildId, roomId).size();
        PageHelper.startPage(pageNum, pageSize);
        List<Security> list = historyService.findCheck(buildId, roomId);
        if (list.size() > 0) {
            map.put("data", list);
            map.put("total", total);
            return RespBean.ok("", map);
        }
        return RespBean.ok("暂无数据");

    }

    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 批量删除
     * @Date 16:24 2019/12/27
     * @Param [ids]
     **/
    @DeleteMapping("deleteBatch/{ids}")
    public RespBean deleteBatch(@PathVariable String ids) {
        if (securityService.deleteBatch(ids)) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
}
