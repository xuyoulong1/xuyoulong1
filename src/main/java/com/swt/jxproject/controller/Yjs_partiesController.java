package com.swt.jxproject.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swt.jxproject.annotation.NoCheck;
import com.swt.jxproject.dto.ResultBody;
import com.swt.jxproject.entity.Yjs_driving_licence;
import com.swt.jxproject.entity.Yjs_ident_card;
import com.swt.jxproject.entity.Yjs_parties;
import com.swt.jxproject.entity.Yjs_vehicle_licence;
import com.swt.jxproject.service.Yjs_driving_licenceService;
import com.swt.jxproject.service.Yjs_ident_cardService;
import com.swt.jxproject.service.Yjs_partiesService;
import com.swt.jxproject.service.Yjs_vehicle_licenceService;
import com.swt.jxproject.utils.NowDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/yjs_parties")
public class Yjs_partiesController {
    @Autowired
    private Yjs_partiesService yjs_partiesService;
    @Autowired
    private Yjs_ident_cardService yjs_ident_cardService;//身份证
    @Autowired
    private Yjs_driving_licenceService yjs_driving_licenceService;//驾驶证
    @Autowired
    Yjs_vehicle_licenceService yjs_vehicle_licenceService;//行驶证


    private final static Logger logger = LoggerFactory.getLogger(Yjs_partiesController.class);
    /**
     * 保存当事人信息,设置现场照片
     * @param jq_id
     * @return
     */
    @PostMapping("/saveParties")
    @NoCheck
    public ResultBody saveParties(@RequestParam("jq_id") Integer jq_id){
        HashMap<String, Object> map = new HashMap<>();
        Yjs_parties yjs_parties = new Yjs_parties();
        BaseMapper<Yjs_parties> baseMapper = yjs_partiesService.getBaseMapper();
        yjs_parties.setJq_id(jq_id);//设置关联的警情id
        yjs_parties.setXc_image("1");//设置是否上传了现场照片
        yjs_parties.setCreate_time(NowDate.NowDate());//设置创建时间
        try {
            int insert = baseMapper.insert(yjs_parties);
            map.put("pts_id",yjs_parties.getPts_id());
        }catch (Exception e){
            logger.error("保存当事人信息失败",e.getMessage());
            return ResultBody.error("500","保存当事人信息失败");
        }

      return   ResultBody.success(map);
    }


    /**
     * 当事人上传证件照
     * @return
     */
    @PostMapping("/savezj_image")
    @NoCheck
    public ResultBody savezj_image(@RequestParam("pts_id") Integer pts_id){
        HashMap<String, Object> map = new HashMap<>();
        Yjs_parties yjs_parties = new Yjs_parties();
        yjs_parties.setZj_image("1");//设置是否上传了证件照
        yjs_parties.setUpdate_time(NowDate.NowDate());//设置更新时间
        BaseMapper<Yjs_parties> baseMapper = yjs_partiesService.getBaseMapper();
        QueryWrapper<Yjs_parties> Wrapper = new QueryWrapper<>();
        Wrapper.eq("pts_id",pts_id);
        try {
            int update = baseMapper.update(yjs_parties, Wrapper);
            map.put("pts_id",yjs_parties.getPts_id());
        }catch (Exception e){
            logger.error("更新当事人上传证件失败",e.getMessage());
            return ResultBody.error("500","更新当事人上传证件失败");
        }
        return   ResultBody.success(map);
    }

    @PostMapping("findpartieszj")
    @NoCheck
    public ResultBody findpartieszj(@RequestParam("pts_id") Integer pts_id){
        HashMap<String, Object> map = new HashMap<>();
        BaseMapper<Yjs_ident_card> yjs_identbaseMapper = yjs_ident_cardService.getBaseMapper();//身份证
        QueryWrapper<Yjs_ident_card> yjs_identWrapper = new QueryWrapper<>();
        yjs_identWrapper.eq("pts_id",pts_id);
        BaseMapper<Yjs_driving_licence> yjs_drivingbaseMapper = yjs_driving_licenceService.getBaseMapper();//驾驶证
        QueryWrapper<Yjs_driving_licence> yjs_drivingWrapper = new QueryWrapper<>();
        yjs_identWrapper.eq("pts_id",pts_id);
        BaseMapper<Yjs_vehicle_licence> yjs_vehiclebaseMapper = yjs_vehicle_licenceService.getBaseMapper();//行驶证
        QueryWrapper<Yjs_vehicle_licence> yjs_vehicleWrapper = new QueryWrapper<>();
        yjs_identWrapper.eq("pts_id",pts_id);
        try {
            List<Yjs_ident_card> yjs_ident_cards = yjs_identbaseMapper.selectList(yjs_identWrapper);//查询身份证
            List<Yjs_driving_licence> yjs_driving_licences = yjs_drivingbaseMapper.selectList(yjs_drivingWrapper);//查询驾驶证
            List<Yjs_vehicle_licence> yjs_vehicle_licences = yjs_vehiclebaseMapper.selectList(yjs_vehicleWrapper);//查询行驶证信息
            map.put("yjs_ident_cards",yjs_ident_cards);
            map.put("yjs_driving_licences",yjs_driving_licences);
            map.put("yjs_vehicle_licences",yjs_vehicle_licences);
        }catch (Exception e){
            logger.error("查询当事人证件信息失败",e.getMessage());
            return ResultBody.error("500","查询当事人证件信息失败");
        }

        return ResultBody.success(map);

    }
}

