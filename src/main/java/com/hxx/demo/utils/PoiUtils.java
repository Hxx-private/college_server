package com.hxx.demo.utils;

import com.hxx.demo.entity.Room;
import com.hxx.demo.entity.User;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sang on 2018/1/16.
 */
public class PoiUtils {

    public static ResponseEntity<byte[]> exportUserExcel(List<User> users) {
        HttpHeaders headers = null;
        ByteArrayOutputStream baos = null;
        try {
            //1.创建Excel文档
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建文档摘要
            workbook.createInformationProperties();
            //3.获取文档信息，并配置
            DocumentSummaryInformation dsi = workbook.getDocumentSummaryInformation();
            //3.1文档类别
            dsi.setCategory("用户信息");
            //3.2设置文档管理员
            dsi.setManager("hxx");
            //3.3设置组织机构
            dsi.setCompany("高校公寓");
            //4.获取摘要信息并配置
            SummaryInformation si = workbook.getSummaryInformation();
            //4.1设置文档主题
            si.setSubject("用户信息表");
            //4.2.设置文档标题
            si.setTitle("用户信息信息");
            //4.3 设置文档作者
            si.setAuthor("XXX大学");
            //4.4设置文档备注
            si.setComments("备注信息暂无");
            //创建Excel表单
            HSSFSheet sheet = workbook.createSheet("高校公寓安检平台用户信息表");
            //创建日期显示格式
            HSSFCellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
            //创建标题的显示样式
            HSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //定义列的宽度
            sheet.setColumnWidth(0, 10 * 256);
            sheet.setColumnWidth(1, 12 * 256);
            sheet.setColumnWidth(2, 10 * 256);
            sheet.setColumnWidth(3, 5 * 256);
            sheet.setColumnWidth(4, 20 * 256);
            sheet.setColumnWidth(5, 10 * 256);
            sheet.setColumnWidth(6, 15 * 256);
            sheet.setColumnWidth(7, 15 * 256);
            sheet.setColumnWidth(8, 10 * 256);
            sheet.setColumnWidth(9, 15* 256);
            sheet.setColumnWidth(10, 10 * 256);
            sheet.setColumnWidth(11, 15 * 256);
            //5.设置表头
            HSSFRow headerRow = sheet.createRow(0);
            HSSFCell cell0 = headerRow.createCell(0);
            cell0.setCellValue("用户ID");
            cell0.setCellStyle(headerStyle);
            HSSFCell cell1 = headerRow.createCell(1);
            cell1.setCellValue("用户名");
            cell1.setCellStyle(headerStyle);
            HSSFCell cell2 = headerRow.createCell(2);
            cell2.setCellValue("姓名");
            cell2.setCellStyle(headerStyle);
            HSSFCell cell3 = headerRow.createCell(3);
            cell3.setCellValue("性别");
            cell3.setCellStyle(headerStyle);
            HSSFCell cell4 = headerRow.createCell(4);
            cell4.setCellValue("注册时间");
            cell4.setCellStyle(headerStyle);
            HSSFCell cell5 = headerRow.createCell(5);
            cell5.setCellValue("年龄");
            cell5.setCellStyle(headerStyle);
            HSSFCell cell6 = headerRow.createCell(6);
            cell6.setCellValue("系别");
            cell6.setCellStyle(headerStyle);
            HSSFCell cell7 = headerRow.createCell(7);
            cell7.setCellValue("专业");
            cell7.setCellStyle(headerStyle);
            HSSFCell cell8 = headerRow.createCell(8);
            cell8.setCellValue("公寓编号");
            cell8.setCellStyle(headerStyle);
            HSSFCell cell9 = headerRow.createCell(9);
            cell9.setCellValue("学号/工号");
            cell9.setCellStyle(headerStyle);
            HSSFCell cell10 = headerRow.createCell(10);
            cell10.setCellValue("宿舍号");
            cell10.setCellStyle(headerStyle);
            HSSFCell cell11 = headerRow.createCell(11);
            cell11.setCellValue("联系电话");
            cell11.setCellStyle(headerStyle);
            //6.装数据
            for (int i = 0; i < users.size(); i++) {
                HSSFRow row = sheet.createRow(i + 1);
                User user = users.get(i);
                row.createCell(0).setCellValue(user.getId());
                row.createCell(1).setCellValue(user.getUsername());
                row.createCell(2).setCellValue(user.getName());
                row.createCell(3).setCellValue(user.getSex());
                HSSFCell birthdayCell = row.createCell(4);
                birthdayCell.setCellValue(user.getRegisterTime());
                birthdayCell.setCellStyle(dateCellStyle);
                row.createCell(5).setCellValue(user.getAge());
                row.createCell(6).setCellValue(user.getDepart());
                row.createCell(7).setCellValue(user.getSpecial());
                row.createCell(8).setCellValue(user.getBuildId());
                row.createCell(9).setCellValue(user.getNumber());
                row.createCell(10).setCellValue(user.getRoomId());
                row.createCell(11).setCellValue(user.getTel());

            }
            headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment",
                    new String("用户表.xls".getBytes("UTF-8"), "iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            baos = new ByteArrayOutputStream();
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }

    public static ResponseEntity<byte[]> exportDormExcel(List<Room> rooms) {
        HttpHeaders headers = null;
        ByteArrayOutputStream baos = null;
        try {
            //1.创建Excel文档
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建文档摘要
            workbook.createInformationProperties();
            //3.获取文档信息，并配置
            DocumentSummaryInformation dsi = workbook.getDocumentSummaryInformation();
            //3.1文档类别
            dsi.setCategory("用户信息");
            //3.2设置文档管理员
            dsi.setManager("hxx");
            //3.3设置组织机构
            dsi.setCompany("高校公寓");
            //4.获取摘要信息并配置
            SummaryInformation si = workbook.getSummaryInformation();
            //4.1设置文档主题
            si.setSubject("用户信息表");
            //4.2.设置文档标题
            si.setTitle("用户信息信息");
            //4.3 设置文档作者
            si.setAuthor("XXX大学");
            //4.4设置文档备注
            si.setComments("备注信息暂无");
            //创建Excel表单
            HSSFSheet sheet = workbook.createSheet("高校公寓安检平台宿舍信息表");
            //创建日期显示格式
            HSSFCellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
            //创建标题的显示样式
            HSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //定义列的宽度
            sheet.setColumnWidth(0, 10 * 256);
            sheet.setColumnWidth(1, 10 * 256);
            sheet.setColumnWidth(2, 10 * 256);
            sheet.setColumnWidth(3, 10 * 256);
            sheet.setColumnWidth(4, 20 * 256);
            //5.设置表头
            HSSFRow headerRow = sheet.createRow(0);
            HSSFCell cell0 = headerRow.createCell(0);
            cell0.setCellValue("公寓编号");
            cell0.setCellStyle(headerStyle);
            HSSFCell cell1 = headerRow.createCell(1);
            cell1.setCellValue("宿舍号");
            cell1.setCellStyle(headerStyle);
            HSSFCell cell2 = headerRow.createCell(2);
            cell2.setCellValue("床位数");
            cell2.setCellStyle(headerStyle);
            HSSFCell cell3 = headerRow.createCell(3);
            cell3.setCellValue("备注");
            cell3.setCellStyle(headerStyle);
            HSSFCell cell4 = headerRow.createCell(4);
            cell4.setCellValue("添加时间");
            cell4.setCellStyle(headerStyle);
            //6.装数据
            for (int i = 0; i < rooms.size(); i++) {
                HSSFRow row = sheet.createRow(i + 1);
                Room room = rooms.get(i);
                row.createCell(0).setCellValue(room.getBuildId());
                row.createCell(1).setCellValue(room.getRoomId());
                row.createCell(2).setCellValue(room.getNum());
                row.createCell(3).setCellValue(room.getRemarks());
                HSSFCell birthdayCell = row.createCell(4);
                birthdayCell.setCellValue(room.getCreateTime());
                birthdayCell.setCellStyle(dateCellStyle);

            }
            headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment",
                    new String("宿舍表.xls".getBytes(StandardCharsets.UTF_8), "iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            baos = new ByteArrayOutputStream();
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }
}
