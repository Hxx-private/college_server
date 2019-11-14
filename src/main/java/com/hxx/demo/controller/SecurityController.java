package com.hxx.demo.controller;


import com.github.pagehelper.PageHelper;
import com.hxx.demo.annotation.UserLoginToken;
import com.hxx.demo.entity.Repair;
import com.hxx.demo.entity.Result;
import com.hxx.demo.entity.Security;
import com.hxx.demo.service.SecurityService;
import com.hxx.demo.utils.DateUtils;
import com.hxx.demo.utils.ExportExcelUtil;
import com.hxx.demo.utils.ExportExcelWrapper;
import com.hxx.demo.utils.IdUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author Hxx
 */


@Api(value = "SanitaryController")
@RestController
@RequestMapping("/api")
public class SecurityController {
    @Autowired
    private SecurityService securityService;

    @ApiOperation(value = "查询所有安全隐患信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum", value = "当前页", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", dataType = "Integer")
    })
    @GetMapping("/security/findAll")
    public Map<String, Object> findAll(Integer pageNum, Integer pageSize) {
        if (securityService.findAll().isEmpty()) {
            return Result.failMap("无安全隐患信息");
        }
        PageHelper.startPage(pageNum, pageSize);
        return Result.successMap(securityService.findAll());
    }


    @ApiOperation(value = "发布安全隐患信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "description", value = "描述", required = true, dataType = "String"),
            @ApiImplicitParam(name = "discover", value = "发布人  由前端获取当前登录用户", required = true, dataType = "String")

    })
    @PostMapping("/security/securityAdd")
    public Map<String, Object> securityAdd(@RequestBody Security security) {
        //设置id
        security.setId(IdUtils.getNumberForPK());
        //处理状态默认为0
        security.setStatus(0);
        //获取系统时间
        security.setDiscoverTime(DateUtils.getSysTime());
        securityService.insertSecurity(security);

        //先存入数据，然后再把数据读出来返回
        if (securityService.findById(security.getId()) == null) {

            return Result.failMap("添加失败");
        } else {
            return Result.successMap(security);
        }
    }

    @ApiOperation(value = "根据宿舍id查找安全隐患信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "roomId", value = "宿舍id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "当前页", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", dataType = "Integer")
    })
    @GetMapping("/security/selectByRoomId/{roomId}")
    public Map<String, Object> selectByRoomId(@PathVariable("roomId") String roomId, Integer pageNum, Integer pageSize) {
        if (securityService.findByRoomId(roomId).isEmpty()) {

            return Result.failMap("该宿舍没有报修过任何东西或请输入正确的宿舍号");
        } else {
            PageHelper.startPage(pageNum, pageSize);
            return Result.successMap(securityService.findByRoomId(roomId));
        }
    }

    @ApiOperation(value = "根据发现人查找安全隐患信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "discover", value = "发现人", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "当前页", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", dataType = "Integer")
    })
    @GetMapping("/security/findByDiscover/{discover}")
    public Map<String, Object> findByDiscover(@PathVariable("discover") String discover, Integer pageNum, Integer pageSize) {
        if (securityService.findByDiscover(discover).isEmpty()) {

            return Result.failMap(discover + "，您没有报修过任何东西");
        } else {
            PageHelper.startPage(pageNum, pageSize);
            return Result.successMap(securityService.findByDiscover(discover));
        }

    }

    @ApiOperation(value = "根据处理状态来查找隐患信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "status", value = "处理状态  0:未处理  1：已处理", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "当前页", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", dataType = "Integer")
    })

    @GetMapping("/security/status/{status}")
    public Map<String, Object> status(@PathVariable("status") Integer status, Integer pageNum, Integer pageSize) {
        if (status == 0 || status == 1) {
            PageHelper.startPage(pageNum, pageSize);
            return Result.successMap(securityService.EventStatus(status));

        } else {
            return Result.failMap("状态码错误");
        }
    }

    @ApiOperation("导出未处理的安全隐患信息表")
    @GetMapping("/security/getExcel")
    public Map<String, Object> getExcel(HttpServletRequest request, HttpServletResponse response) {
        List<Security> securitity = securityService.EventStatus(0);
        String[] columnNames = {"ID", "描述", "发现人", "处理人", "状态", "发现时间", "房间号"};
        String fileName = "事件未处理表";
        ExportExcelWrapper<Security> util = new ExportExcelWrapper<Security>();
        util.exportExcel(fileName, fileName, columnNames, securitity, response, ExportExcelUtil.EXCEL_FILE_2003);

        return Result.successMap("下载成功");

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
    public Map<String, Object> delBySeRoomId(@PathVariable("roomId") String roomId) {
        securityService.delBySeroomId(roomId);
        if (securityService.findByRoomId(roomId) == null) {
            return Result.successMap("删除成功");
        }
        return Result.failMap("删除失败");
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
    public Map<String, Object> delByoperateTime(@PathVariable("operateTime") String operateTime) {
        securityService.delByoperateTime(operateTime);
        if (securityService.findByOpTime(operateTime).isEmpty()) {
            return Result.successMap("删除成功");
        }
        return Result.failMap("删除失败");
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
    public Map<String, Object> handleSecurity(@RequestBody Security security) {
        securityService.findById(security.getId());
        security.setStatus(1);
        security.setOperateTime(DateUtils.getSysTime());
        securityService.handleSecurity(security);
        if (securityService.findById(security.getId()).getStatus() == 1) {
            return Result.successMap(security);
        }
        return Result.failMap("处理失败");
    }
    //将没有修好的所有信息生成一个excel表格并下载到本地
    /*@GetMapping("/security/ExcelDownloads")
    public void ExcelDownloads(HttpServletResponse response) throws IOException {
        List<Security> exceldownload = securityService.EventStatus(0);
        System.out.println("------------"+exceldownload.toString());
        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("获取excel测试表格");

        HSSFRow row = null;

        row = sheet.createRow(0);//创建第一个单元格
        row.setHeight((short) (26.25 * 20));
        row.createCell(0).setCellValue("用户信息列表");//为第一行单元格设值

        *//*为标题设计空间
     * firstRow从第1行开始
     * lastRow从第0行结束
     *
     *从第1个单元格开始
     * 从第3个单元格结束
     *//*
        CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 2);
        sheet.addMergedRegion(rowRegion);

      *//*CellRangeAddress columnRegion = new CellRangeAddress(1,4,0,0);
      sheet.addMergedRegion(columnRegion);*//*

        row = sheet.createRow(1);
        row.setHeight((short) (22.50 * 20));//设置行高
        row.createCell(0).setCellValue("Id");//为第一个单元格设值
        row.createCell(1).setCellValue("description");//为第二个单元格设值
        row.createCell(2).setCellValue("discover");//为第三个单元格设值
        row.createCell(3).setCellValue("operator");//为第四个单元格设值
        row.createCell(4).setCellValue("status");//为第五个单元格设值
        row.createCell(5).setCellValue("discoverTime");//为第六个单元格设值
        row.createCell(6).setCellValue("roomId");//为第七个单元格设值

        //遍历所获取的数据
        for (int i = 0; i < exceldownload.size(); i++) {
            row = sheet.createRow(i + 2);
            Security bgm = exceldownload.get(i);
            row.createCell(0).setCellValue(bgm.getId());
            row.createCell(1).setCellValue(bgm.getDescription());
            row.createCell(2).setCellValue(bgm.getDiscover());
            row.createCell(3).setCellValue(bgm.getOperator());
            row.createCell(4).setCellValue("未处理");
            row.createCell(5).setCellValue(bgm.getDiscoverTime());
            row.createCell(6).setCellValue(bgm.getRoomId());
        }
        sheet.setDefaultRowHeight((short) (16.5 * 20));

        //列宽自适应
        for (int i = 0; i <= 13; i++) {
            sheet.autoSizeColumn(i);
        }
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream os = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename=BGM.xls");//默认Excel名称
        wb.write(os);
        os.flush();
        os.close();

    }*/
}
