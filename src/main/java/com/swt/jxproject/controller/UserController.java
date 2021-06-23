package com.swt.jxproject.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swt.jxproject.annotation.NoCheck;
import com.swt.jxproject.dto.ResultBody;
import com.swt.jxproject.dto.UserDto;
import com.swt.jxproject.entity.User;
import com.swt.jxproject.entity.emp;
import com.swt.jxproject.service.UserService;
import com.swt.jxproject.utils.*;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.DriverLicenseOCRResponse;
import com.tencentcloudapi.ocr.v20181119.models.IDCardOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.IDCardOCRResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private com.swt.jxproject.service.se see;


    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping("ll")
    @NoCheck
    public void setValue() {
        //设置过期时间为1分钟
        redisTemplate.boundValueOps("name").set("youjiuye",60, TimeUnit.SECONDS);
        String name = (String) redisTemplate.boundValueOps("name").get();
        System.out.println(name.toString());
    }

    @GetMapping("eqname")
    public UserDto eqName(String name){
        UserDto userByNameEq = userService.findUserByNameEq(name);

        return userByNameEq;
    }


    @GetMapping("hello")
    public String hello(String name){
        UserDto userByNameEq = userService.findUserByNameEq(name);
        System.out.println(userByNameEq);
        System.out.println(redisTemplate);
        return "hello";
    }

    @NoCheck
    @PostMapping("login")
    public String hello(@Valid @RequestBody UserDto userDto) throws Exception {
        JWTUtils jWTUtils = ApplicationContextUtil.getbean("jwtutils", JWTUtils.class);
        String token = jWTUtils.createToken(userDto.getId().toString(),userDto.getUsername());
        return token;
    }
    @NoCheck
    @PostMapping("l")
    public long hello1() throws Exception {
        IPage<emp> empIPage = see.test4();
        return empIPage.getTotal();
    }


    @NoCheck
    @PostMapping("/lp")
    public ResultBody ImgFileToBase64Str(@RequestParam("img")MultipartFile img) throws Exception {
        File file1 = MutipartFileToFile.multipartFileToFile(img);
        String s = MutipartFileToFile.ImgFileToBase64Str(file1);
        IDCardOCRResponse idCardOCRResponse = OCRUtils.IDCardOCR(s);
        if(idCardOCRResponse==null){
            return ResultBody.error("500","识别错误");
        }
        return ResultBody.success();
    }


    @NoCheck
    @PostMapping("/lu")
    public ResultBody Img(@RequestParam("img")MultipartFile img) throws Exception {
        File file1 = MutipartFileToFile.multipartFileToFile(img);
        String s = MutipartFileToFile.ImgFileToBase64Str(file1);
        DriverLicenseOCRResponse driverLicenseOCRResponse = OCRUtils.DriverLicenseOCR(s);
//        if(idCardOCRResponse==null){
//            return ResultBody.error("500","识别错误");
//        }
        return ResultBody.success();
    }

    @PostMapping("/kkkk")
    @NoCheck
    public ResultBody p( HttpServletRequest request, HttpServletResponse response) throws Exception {
            List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                    .getFiles("file");
        List<String> strings = Upload.uploadImgList(files, request, response);
        return ResultBody.success();
    }


}
