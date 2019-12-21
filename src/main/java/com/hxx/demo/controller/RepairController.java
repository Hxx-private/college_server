package com.hxx.demo.controller;

import com.github.pagehelper.PageHelper;
import com.hxx.demo.entity.*;
import com.hxx.demo.service.RepairService;
import com.hxx.demo.utils.DateUtils;
import com.hxx.demo.utils.IdUtils;
import com.hxx.demo.utils.UserUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hxx
 */

@Api(value = "报修模块")
@RestController
@RequestMapping("/repair/rep")
public class RepairController {
    @Autowired
    private RepairService repairService;

    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 申请报修列表
     * @Date 8:25 2019/12/19
     * @Param [pageNum, pageSize]
     **/
    @GetMapping("apply/findList")
    public RespBean findList(Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        String roomId = UserUtils.getCurrentUser().getRoomId();
        Integer buildId = UserUtils.getCurrentUser().getBuildId();
        List<Repair> repairs = repairService.findAllRepair();
        List<Repair> filterLists = new ArrayList<>();
        int num = 0;
        for (Repair repair : repairs) {
            if (roomId.equals(repair.getRoomId()) && buildId == repair.getBuildId()) {
                filterLists.add(repair);
                num++;
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Repair> list = repairService.findAllRepair();
        List<Repair> filterList = new ArrayList<>();
        for (Repair repair : list) {
            if (roomId.equals(repair.getRoomId()) && buildId == repair.getBuildId()) {
                filterList.add(repair);
            }
        }
        if (filterList.size() > 0) {
            int total = num;
            map.put("data", filterList);
            map.put("total", total);
            return RespBean.ok("", map);
        }

        return RespBean.ok("暂无数据");

    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 申请报修功能
     * @Date 17:19 2019/10/31
     * @Param [repair]
     **/
    @PostMapping("apply/add")
    public RespBean createRepair(@RequestBody Repair repair) {
        repair.setId(IdUtils.getNumberForPK());
        repair.setBuildId(UserUtils.getCurrentUser().getBuildId());
        repair.setRoomId(UserUtils.getCurrentUser().getRoomId());
        repair.setApplicant(UserUtils.getCurrentUser().getName());
        repair.setStatus(0);
        repair.setTime(DateUtils.getSysTime());
        int i = repairService.create(repair);
        if (i > 0) {

            return RespBean.ok("提交成功", repair);
        }
        return RespBean.error("提交失败");
    }

    /**
     * @return com.hxx.demo.entity.Result
     * @Author Hxx
     * @Description //TODO 处理报修
     * @Date 10:36 2019/11/1
     * @Param []
     **/
    @PutMapping("handle/{id}")
    public RespBean handleRepair(@PathVariable String id) {
        Repair repair = new Repair();
        repair.setId(id);
        repair.setOperator(UserUtils.getCurrentUser().getName());
        repair.setStatus(1);
        repair.setEndTime(DateUtils.getSysTime());
        int i = repairService.handleRepair(repair);
        if (i > 0) {
            return RespBean.ok("维修完成", repair);
        }
        return RespBean.error("操作失败");
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 报修列表
     * @Date 9:47 2019/10/30
     * @Param []
     **/
    @GetMapping("pending/list")
    public RespBean findAllRepair(Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        int total = repairService.findAllRepair().size();
        PageHelper.startPage(pageNum, pageSize);
        List<Repair> list = repairService.findAllRepair();
        map.put("data", list);
        map.put("total", total);
        return RespBean.ok("", map);
    }


    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 历史记录
     * @Date 9:03 2019/11/8
     * @Param [roomId]
     **/
    @GetMapping("history/record")
    public RespBean findByStatus(Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        int total = repairService.findByStatus().size();
        PageHelper.startPage(pageNum, pageSize);
        List<Repair> list = repairService.findByStatus();
        map.put("data", list);
        map.put("total", total);
        return RespBean.ok("", map);
    }

    /**
     * @return
     * @Author Hxx
     * @Description //TODO 根据指定字段查询维修记录
     * @Date 11:53 2019/12/2
     * @Param
     **/
    @PostMapping(value = "findByKeyWords", consumes = "application/json;charset=UTF-8")
    public HttpEntity findByKeyWords(@RequestBody GridRequest gridJson) {
        HttpEntity httpEntity = new HttpEntity();
        Grid grid = new Grid();
        PageHelper.startPage(gridJson.getPageNum(), gridJson.getPageSize());
        List<Repair> list = this.repairService.getGrid(gridJson);
        int total = repairService.total();
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
     * @Description //TODO 清空维修记录
     * @Date 17:05 2019/12/13
     * @Param []
     **/
    @DeleteMapping("deleteRecord")
    public RespBean deleteRecord() {
        int i = repairService.deleteRecord();
        if (i > 0) {
            return RespBean.ok("数据已清空");
        }
        return RespBean.error("操作失败");
    }


    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 根据id删除维修记录
     * @Date 17:33 2019/12/13
     * @Param [id]
     **/
    @DeleteMapping("apply/delById/{id}")
    public RespBean deleteById(@PathVariable String id) {
        int i = repairService.deleteById(id);
        if (i > 0) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 编辑报修信息
     * @Date 8:33 2019/12/19
     * @Param [id]
     **/
    @PostMapping("apply/update")
    public RespBean update(@RequestBody Repair repair) {
        repair.setTime(DateUtils.getSysTime());
        int i = repairService.update(repair);
        if (i > 0) {
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败");
    }

    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO  维修记录
     * @Date 11:00 2019/12/20
     * @Param [pageNum, pageSize]
     **/
    @GetMapping("repair/record")
    public RespBean repairRecord(Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        String roomId = UserUtils.getCurrentUser().getRoomId();
        Integer buildId = UserUtils.getCurrentUser().getBuildId();
        List<Repair> repairs = repairService.findByStatus();
        List<Repair> filterLists = new ArrayList<>();
        int num = 0;
        for (Repair repair : repairs) {
            if (roomId.equals(repair.getRoomId()) && buildId == repair.getBuildId()) {
                PageHelper.startPage(pageNum, pageSize);
                filterLists.add(repair);
                num++;
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Repair> list = repairService.findByStatus();
        List<Repair> filterList = new ArrayList<>();
        for (Repair repair : list) {
            if (roomId.equals(repair.getRoomId()) && buildId == repair.getBuildId()) {
                filterList.add(repair);
            }
        }
        if (filterLists.size() > 0) {
            int total = num;
            map.put("data", filterList);
            map.put("total", total);
            return RespBean.ok("", map);
        }

        return RespBean.ok("暂无数据");
    }


    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 批量删除维修记录
     * @Date 17:34 2019/12/13
     * @Param [ids]
     **/
    @DeleteMapping("deleteBatch/{ids}")
    public RespBean deleteBatch(@PathVariable String ids) {
        if (repairService.deleteBatch(ids)) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
}
