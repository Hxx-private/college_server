package com.hxx.demo.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传工具包
 */
public class FileUtils {

    /**
     *
     * @param file 文件
     * @param realPath 文件存放路径
     * @param fileName 源文件名
     * @return
     */
    public static boolean upload(MultipartFile file, String realPath, String fileName){


        // 生成新的文件名
        //String realPath = path + "/" + FileNameUtils.getFileName(fileName);


        File targetFile = new File(realPath);
        System.out.println("filePath="+realPath);
        //获取父级文件夹
        File fileParent = targetFile.getParentFile();
        //判断该文件夹或者该文件是否存在，不存在则创建
        if(!fileParent.exists()){

            System.out.println("该路径不存在,接下来创建");
            //先创建父级目录，然后再
            fileParent.mkdirs();
            targetFile.mkdir();

            System.out.println("创建成功");
        }
        File outFile = new File(targetFile,fileName);

        try {
            //保存文件
            file.transferTo(outFile);
            return true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }


}
