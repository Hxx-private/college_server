package com.hxx.demo.controller;


import com.hxx.demo.config.ServerConfig;
import com.hxx.demo.entity.RespBean;
import com.hxx.demo.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class ImageController {
    @Autowired
    private ServerConfig serverConfig;
    @Value("${web.upload-path}")
    private String path;
    public  static String imgUrl = "";
    /**
     * @param file 要上传的文件
     * @return
     */
    @RequestMapping("img/fileUpload")
    public RespBean upload(@RequestParam("file") MultipartFile file) {
        if (FileUtils.upload(file, path, file.getOriginalFilename())) {
            imgUrl =serverConfig.getUrl() +path.substring(2,path.length())+file.getOriginalFilename();
            System.out.println(imgUrl);
            return RespBean.ok("获取成功");
        } else {
            return RespBean.error("获取失败");

        }

    }


}
