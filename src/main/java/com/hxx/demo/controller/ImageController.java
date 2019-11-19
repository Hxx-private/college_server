package com.hxx.demo.controller;

import com.hxx.demo.entity.Result;
import com.hxx.demo.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@RestController
public class ImageController {

    @Value("${web.upload-path}")
    private String path;
    /**
     *
     * @param file 要上传的文件
     * @return
     */
    @RequestMapping("fileUpload")
    public Map<String,Object> upload(@RequestParam("fileName") MultipartFile file){
        // 上传成功或者失败的提示
//        System.out.println("path:"+path);
//        System.out.println(file);
        if (FileUtils.upload(file, path, file.getOriginalFilename())){
            // 上传成功，给出页面提示
           return Result.successMap("上传成功");
        }else {
            return Result.failMap("上传失败");

        }


    }
}
