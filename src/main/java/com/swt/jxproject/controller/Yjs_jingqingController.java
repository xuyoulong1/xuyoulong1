package com.swt.jxproject.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swt.jxproject.annotation.NoCheck;
import com.swt.jxproject.dto.ResultBody;
import com.swt.jxproject.entity.Yjs_jingqing;
import com.swt.jxproject.service.Yjs_jingqingService;
import com.swt.jxproject.utils.NowDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xyl
 * @since 2021-06-18
 */
@RestController
@RequestMapping("/yjs_jingqing")
public class Yjs_jingqingController {

    @Autowired
    private Yjs_jingqingService yjs_jingqingService;

    /**
     * 保存报警信息
     * @param yjs_jingqing
     * @return
     */
    @PostMapping("/savejinqing")
    @NoCheck
    public ResultBody savejingqing (@RequestBody Yjs_jingqing yjs_jingqing){
        BaseMapper<Yjs_jingqing> baseMapper = yjs_jingqingService.getBaseMapper();
        yjs_jingqing.setCreate_time(NowDate.NowDate());
        yjs_jingqing.setStatus("0");
        int insert = baseMapper.insert(yjs_jingqing);
        Integer jq_id = yjs_jingqing.getJq_id();//返回保存的id
        String jqbh = yjs_jingqing.getJQBH();//返回情指勤督编号(这个是对接返回的数据占时是弄假的)
        HashMap<String, Object> map = new HashMap<>();
        map.put("jq_id",jq_id);
        map.put("jqbh",jqbh);
        return ResultBody.success(map);
    }

    /**
     * 查询报警人所有的报警案例
     * @param id
     * @return
     */
    @PostMapping("findusesbaojing")
    @NoCheck
    public ResultBody findAll(@RequestParam("user_id") String id){
        BaseMapper<Yjs_jingqing> baseMapper = yjs_jingqingService.getBaseMapper();
        QueryWrapper<Yjs_jingqing> Wrapper = new QueryWrapper<>();
        Wrapper.eq("user_id",id);
        Wrapper.orderByDesc("create_time");
        List<Yjs_jingqing> list = baseMapper.selectList(Wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",list);
        return ResultBody.success(map);
    }
}

