package com.swt.jxproject.controller;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swt.jxproject.annotation.NoCheck;
import com.swt.jxproject.dto.ResultBody;
import com.swt.jxproject.entity.Phone;
import com.swt.jxproject.entity.Rest;
import com.swt.jxproject.entity.Yjs_user;
import com.swt.jxproject.service.Yjs_userService;
import com.swt.jxproject.utils.NowDate;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xyl
 * @since 2021-06-18
 */
@RestController
@RequestMapping("/yjs_user")
public class Yjs_userController {

    @Autowired
    private Yjs_userService yjs_userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 手机进行登录
     *
     * @param phone
     * @return
     */
    @PostMapping("/phone")
    @NoCheck
    public ResultBody login(String phone) {
        //设置过期时间为1分钟
        redisTemplate.boundValueOps("phone").set(phone, 60, TimeUnit.SECONDS);
        String phone1 = (String) redisTemplate.boundValueOps("phone").get();
        //如果匹配成功存电话
        Yjs_user yjs_user = new Yjs_user();
        yjs_user.setPhone(phone);
        String createDate = NowDate.NowDate();
        yjs_user.setCreate_time(createDate);
        BaseMapper<Yjs_user> baseMapper = yjs_userService.getBaseMapper();
        baseMapper.insert(yjs_user);
        System.out.println(phone1.toString());
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", yjs_user.getUser_id());
        return ResultBody.success(map);
    }

    @PostMapping("/send")
    @NoCheck
    public ResultBody sendPhone(@RequestParam("phone")String number) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        Phone phone = new Phone();
        phone.setAppKey("a1fda3bd92c4483db7e42c5f417bda77");
        phone.setMobile(number);
        phone.setText("1111");
        JSONObject jsonObj = JSONObject.fromObject(phone);
        HttpEntity<String> formEntity = new HttpEntity<>(jsonObj.toString(), headers);
        //ResponseEntity<String> urlEntity = restTemplate.postForEntity(caseReportUrl, formEntity, String.class);
        ResponseEntity<Rest> urlEntity = restTemplate.postForEntity("http://192.168.0.12:17173/sms/message/single", formEntity, Rest.class);
        //ResponseEntity<Rest> urlEntity = restTemplate.postForEntity("http://127.0.0.1:8888/yjs_user/phone", formEntity, Rest.class);
    return ResultBody.success();
    }

}

