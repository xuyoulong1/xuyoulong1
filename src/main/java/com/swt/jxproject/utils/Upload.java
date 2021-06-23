package com.swt.jxproject.utils;

import com.swt.jxproject.controller.ImageUpload;
import com.swt.jxproject.dto.ResultBody;
import com.swt.jxproject.service.Yjs_imageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Upload {
    private final static Logger logger = LoggerFactory.getLogger(Upload.class);

    private  static  String imgnameprefix="XC";
    private  static  String imgnamesuffix=".jpg";
    /**
     * 单张图片上传
     * @param img
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public static String uploadImg(MultipartFile img, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String contentType = img.getContentType();    // 获取文件的类型
        System.out.println("文件类型为：" +  contentType);
        String originalFilename = img.getOriginalFilename();     // 获取文件的原始名称
        String name=null;
        // 判断文件是否为空
        if(!img.isEmpty()) {
            File targetImg = new File("D:/img");
            // 判断文件夹是否存在
            if(!targetImg.exists()) {
                targetImg.mkdirs();    //级联创建文件夹
            }
            try {
                // 开始保存图片
                String uuid = UUID.randomUUID().toString().replaceAll("-","");
                name=uuid+originalFilename;
                FileOutputStream outputStream = new FileOutputStream("D:/img/"+imgnameprefix+uuid+originalFilename);
                outputStream.write(img.getBytes());
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                logger.error("上传图片失败",e.getMessage());
                e.printStackTrace();
                throw new Exception("上传图片失败");
            }
        }
        return name;
    }

    /**
     * 多张图片上传
     * @param img
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public static List<String> uploadImgList(List<MultipartFile> img, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ArrayList<String> list = new ArrayList<>();
        for (MultipartFile multipartFile : img) {
            String contentType = multipartFile.getContentType();    // 获取文件的类型
            System.out.println("文件类型为：" +  contentType);
            String originalFilename = multipartFile.getOriginalFilename();     // 获取文件的原始名称
            String name=null;
            FileOutputStream outputStream = null;
            // 判断文件是否为空
            if(!multipartFile.isEmpty()) {
                File targetImg = new File("D:/img");
                // 判断文件夹是否存在
                if(!targetImg.exists()) {
                    targetImg.mkdirs();    //级联创建文件夹
                }
                try {
                    // 开始保存图片
                    String uuid = UUID.randomUUID().toString().replaceAll("-","");
                    name=uuid+originalFilename;
                     outputStream = new FileOutputStream("D:/img/"+imgnameprefix +uuid+originalFilename);
                    outputStream.write(multipartFile.getBytes());

                    list.add(name);
                } catch (IOException e) {
                    logger.error("上传图片失败",e.getMessage());
                    return  null;
                }finally {
                    outputStream.flush();
                    outputStream.close();
                }
            }
        }

        return list;
    }


}
