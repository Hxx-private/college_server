package com.hxx.demo.controller;

import com.github.pagehelper.PageHelper;
import com.hxx.demo.config.ServerConfig;
import com.hxx.demo.entity.*;
import com.hxx.demo.service.LostService;
import com.hxx.demo.utils.DateUtils;
import com.hxx.demo.utils.IdUtils;
import com.hxx.demo.utils.UserUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private ServerConfig serverConfig;
    @Value("${web.upload-path}")
    private String path;


    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 根据指定时间段查询丢失信息
     * @Date 11:15 2019/10/31
     * @Param [periodTime]
     **/
    @PostMapping("/los/getByPeriod")
    public RespBean getLostByPeriod(@RequestBody PeriodTime time) {
        if (lostService.getLostByPeriod(time).isEmpty()) {
            return RespBean.ok("所选时间段内无丢失物品");
        }
        int total = lostService.getLostByPeriod(time).size();
        PageHelper.startPage(time.getPageNum(), time.getPageSize());
        List<Lost> list = lostService.getLostByPeriod(time);
        if (total > 0) {
            return RespBean.ok("", list);
        }
        return RespBean.ok("暂无数据");
    }


    /**
     * @return com.hxx.demo.entity.HttpEntity
     * @Author Hxx
     * @Description //TODO 根据关键字搜索失物信息
     * @Date 10:06 2019/12/12
     * @Param [gridJson]
     **/
    @RequestMapping(value = "/findByKeyWords/{keywords}")
    public RespBean findByKeyWords(@PathVariable("keywords") String keywords) {
        List<Lost> list = lostService.findByKewords(keywords);
        if (list.size() > 0) {
            return RespBean.ok("", list);
        }
        return RespBean.ok("暂无数据");
    }


    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 发布失物信息
     * @Date 9:46 2019/10/30
     * @Param [lost]
     **/
    @PostMapping("/los/addLost")
    public RespBean addLost(@RequestBody Lost lost) {
        lost.setCreater(UserUtils.getCurrentUser().getName());
        lost.setId(IdUtils.getNumberForPK());
        lost.setImgUrl(ImageController.imgUrl);
        lost.setCreateTime(DateUtils.getSysTime());
        int i = lostService.insertLost(lost);
        if (i == 1) {
            return RespBean.ok("发布成功", lost);
        }

        return RespBean.ok("发布失败");
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
        int total = lostService.findAllLost().size();
        PageHelper.startPage(pageNum, pageSize);
        List<Lost> list = lostService.findAllLost();
        map.put("data", list);
        map.put("total", total);
        return RespBean.ok("", map);
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
        String creater = UserUtils.getCurrentUser().getName();
        int total = lostService.findAllHistoryLost(creater).size();
        PageHelper.startPage(pageNum, pageSize);
        List<Lost> list = lostService.findAllHistoryLost(creater);
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
     * @Description //TODO 历史记录
     * @Date 14:38 2019/12/23
     * @Param []
     **/

    @DeleteMapping("/los/deleteAllHistory")
    public RespBean deleteAll() {
        if (lostService.deleteAll() != 0) {
            return RespBean.ok("数据已经清空");
        }
        return RespBean.error("操作失败");
    }
}
