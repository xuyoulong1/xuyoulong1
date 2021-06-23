package com.swt.jxproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swt.jxproject.annotation.NoCheck;
import com.swt.jxproject.dto.ResultBody;
import com.swt.jxproject.entity.Yjs_driving_licence;
import com.swt.jxproject.entity.Yjs_ident_card;
import com.swt.jxproject.entity.Yjs_image;
import com.swt.jxproject.entity.Yjs_vehicle_licence;
import com.swt.jxproject.mapper.Yjs_imageMapper;
import com.swt.jxproject.service.Yjs_driving_licenceService;
import com.swt.jxproject.service.Yjs_ident_cardService;
import com.swt.jxproject.service.Yjs_vehicle_licenceService;
import com.swt.jxproject.service.impl.Yjs_imageServiceImpl;
import com.swt.jxproject.utils.MutipartFileToFile;
import com.swt.jxproject.utils.NowDate;
import com.swt.jxproject.utils.OCRUtils;
import com.swt.jxproject.utils.Upload;
import com.tencentcloudapi.ocr.v20181119.models.DriverLicenseOCRResponse;
import com.tencentcloudapi.ocr.v20181119.models.IDCardOCRResponse;
import com.tencentcloudapi.ocr.v20181119.models.LicensePlateOCRResponse;
import com.tencentcloudapi.ocr.v20181119.models.VehicleLicenseOCRResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@RestController
public class ImageUpload {

    @Autowired
    private Yjs_imageServiceImpl yjs_imageService;
    @Autowired
    private Yjs_ident_cardService yjs_ident_cardService;
    @Autowired
    private Yjs_driving_licenceService yjs_driving_licenceService;
    @Autowired
    private Yjs_vehicle_licenceService yjs_vehicle_licenceService;

    private final static Logger logger = LoggerFactory.getLogger(ImageUpload.class);
    /**
     * 省份证识别
     * @param img
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/uploadIdCard")
    @NoCheck
    public ResultBody uploadImgIdCard(@RequestParam("pts_id") String pts_id,@RequestParam("card_id") String id,@RequestParam("img") MultipartFile img, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer userid = Integer.valueOf(pts_id);
        File file1 = MutipartFileToFile.multipartFileToFile(img);
        String base64 = MutipartFileToFile.ImgFileToBase64Str(file1);
        IDCardOCRResponse idCardOCRResponse = OCRUtils.IDCardOCR(base64);
        if (idCardOCRResponse==null){
            return ResultBody.error("500","识别错误");
        }
        String name = Upload.uploadImg(img, request, response);
        if (name==null){
            return ResultBody.error("500","上传文件失败");
        }
        Map<String, Object> map = new HashMap<>();
        if(id==null|| id.equals("")||id.equals("null")) {
            Integer img_id=null;
            try {
                //保存图片
                Yjs_image yjs_image = new Yjs_image();
                yjs_image.setImg_name(name);
                yjs_image.setFlag("1");
                yjs_image.setPts_id(userid);
                yjs_image.setImg_type("1");
                yjs_image.setCreate_time(NowDate.NowDate());
                Yjs_imageMapper baseMapper = yjs_imageService.getBaseMapper();
                int insert = baseMapper.insert(yjs_image);
                 img_id = yjs_image.getImg_id();
            }catch (Exception e){
                logger.error("保存身份证图片出错",e.getMessage());
            }
            Yjs_ident_card yjs_ident_card = new Yjs_ident_card();
           try {
               //保存身份证信息
               if (idCardOCRResponse.getSex() == null||idCardOCRResponse.getSex().equals("")) {
                   yjs_ident_card.setPts_id(userid);//设置当事人
                   yjs_ident_card.setCreate_time(NowDate.NowDate());//设置创建时间
                   yjs_ident_card.setAuthority(idCardOCRResponse.getAuthority());//设置发证机关
                   yjs_ident_card.setValid_date(idCardOCRResponse.getValidDate());//设置有效期
               } else {
                   yjs_ident_card.setImg_id(img_id);//设置图片id
                   yjs_ident_card.setPts_id(userid);//设置当事人
                   yjs_ident_card.setAddress(idCardOCRResponse.getAddress());//设置地址
                   yjs_ident_card.setCreate_time(NowDate.NowDate());//设置创时间
                   yjs_ident_card.setBirth(idCardOCRResponse.getBirth());//设置生日
                   yjs_ident_card.setName(idCardOCRResponse.getName());//设置名字
                   yjs_ident_card.setCard_num(idCardOCRResponse.getIdNum());//设置身份证号
                   yjs_ident_card.setSex(idCardOCRResponse.getSex());//设置性别
                   yjs_ident_card.setNation(idCardOCRResponse.getNation());//设置性别
               }
               BaseMapper<Yjs_ident_card> baseMapper1 = yjs_ident_cardService.getBaseMapper();
               int insert1 = baseMapper1.insert(yjs_ident_card);
               map.put("img_id", img_id);
               map.put("card_id", yjs_ident_card.getIc_id());
               map.put("pts_id", pts_id);
           }catch (Exception e){
               logger.error("保存身份证图片信息出错",e.getMessage());
           }
        }else{
            Integer img_id=null;
            try {
                //保存图片
                Yjs_image yjs_image = new Yjs_image();
                yjs_image.setImg_name(name);
                yjs_image.setFlag("1");
                yjs_image.setPts_id(userid);
                yjs_image.setImg_type("1");
                yjs_image.setCreate_time(NowDate.NowDate());
                Yjs_imageMapper baseMapper = yjs_imageService.getBaseMapper();
                int insert = baseMapper.insert(yjs_image);
                img_id = yjs_image.getImg_id();
            }catch (Exception e){
                logger.error("保存身份证图片出错",e.getMessage());
            }
            Yjs_ident_card yjs_ident_card = new Yjs_ident_card();
            try {
                //保存身份证信息
                if (idCardOCRResponse.getSex() == null||idCardOCRResponse.getSex().equals("")) {
                    yjs_ident_card.setUpdate_time(NowDate.NowDate());//设置更新
                    yjs_ident_card.setAuthority(idCardOCRResponse.getAuthority());//设置发证机关
                    yjs_ident_card.setPts_id(userid);//设置当事人id
                    yjs_ident_card.setValid_date(idCardOCRResponse.getValidDate());//设置有效期
                } else {
                    yjs_ident_card.setImg_id(img_id);//设置图片id
                    yjs_ident_card.setPts_id(userid);//设置当事人id
                    yjs_ident_card.setAddress(idCardOCRResponse.getAddress());//设置地址
                    yjs_ident_card.setUpdate_time(NowDate.NowDate());//设置更新
                    yjs_ident_card.setBirth(idCardOCRResponse.getBirth());//设置生日
                    yjs_ident_card.setName(idCardOCRResponse.getName());//设置名字
                    yjs_ident_card.setCard_num(idCardOCRResponse.getIdNum());//设置身份证号
                    yjs_ident_card.setSex(idCardOCRResponse.getSex());//设置性别
                    yjs_ident_card.setNation(idCardOCRResponse.getNation());//设置性别
                }
                BaseMapper<Yjs_ident_card> baseMapper1 = yjs_ident_cardService.getBaseMapper();
                QueryWrapper<Yjs_ident_card> Query = new QueryWrapper<>();
                Query.eq("pts_id",pts_id);
                int update = baseMapper1.update(yjs_ident_card, Query);
                map.put("img_id", img_id);
                map.put("card_id", id);
                map.put("pts_id",pts_id);
            }catch (Exception e){
                logger.error("更新身份证图片出错",e.getMessage());
            }
        }
        return ResultBody.success(map);
    }

    /**
     * 修改图片状态
     * @param img_id
     * @return
     */
    @ResponseBody
    @PostMapping("/updatefalg")
    @NoCheck
    public ResultBody updateflag(@RequestParam("img_id")String img_id){
        Yjs_imageMapper baseMapper = yjs_imageService.getBaseMapper();
        QueryWrapper<Yjs_image> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("img_id",img_id);
        Yjs_image yjs_image = new Yjs_image();
        yjs_image.setUpdate_time(NowDate.NowDate());
        yjs_image.setFlag("0");
        int update = baseMapper.update(yjs_image, QueryWrapper);
        return ResultBody.success();
    }

    /**
     * 识别驾驶证
     * @param img
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/uploaddriver")
    @NoCheck
    public ResultBody uploadImgdriver(@RequestParam("pts_id") String pts_id,@RequestParam("card_id") String id,@RequestParam("img") MultipartFile img, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer userid = Integer.valueOf(pts_id);
        File file1 = MutipartFileToFile.multipartFileToFile(img);
        String base64 = MutipartFileToFile.ImgFileToBase64Str(file1);
        DriverLicenseOCRResponse driverLicenseOCRResponse = OCRUtils.DriverLicenseOCR(base64);
        if (driverLicenseOCRResponse==null){
            return ResultBody.error("500","识别错误");
        }
        String name = Upload.uploadImg(img, request, response);
        Map<String, Object> map = new HashMap<>();
        if (name == null) {
            return ResultBody.error("500", "上传文件失败");
        }
        if (id == null || id.equals("")||id.equals("null")) {
            Integer img_id=null;
            try {
                //保存图片
                Yjs_image yjs_image = new Yjs_image();
                yjs_image.setImg_name(name);
                yjs_image.setFlag("1");
                yjs_image.setImg_type("2");
                yjs_image.setPts_id(userid);
                yjs_image.setCreate_time(NowDate.NowDate());
                Yjs_imageMapper baseMapper = yjs_imageService.getBaseMapper();
                baseMapper.insert(yjs_image);
                img_id = yjs_image.getImg_id();
            }catch (Exception e){
                logger.error("保存驾驶证图片出错",e.getMessage());
            }
            Yjs_driving_licence yjs_driving_licence = new Yjs_driving_licence();
            try {
                //保存驾驶证信息
                if (driverLicenseOCRResponse.getSex() == null||driverLicenseOCRResponse.getSex().equals("")) {
                    yjs_driving_licence.setPts_id(userid);//设置当事人
                    yjs_driving_licence.setArchives_code(driverLicenseOCRResponse.getArchivesCode());//设置档案编号
                    BaseMapper<Yjs_driving_licence> baseMapper1 = yjs_driving_licenceService.getBaseMapper();
                    int insert = baseMapper1.insert(yjs_driving_licence);
                    map.put("img_id", img_id);
                    map.put("pts_id",pts_id);
                    map.put("card_id", yjs_driving_licence.getDl_id());
                }else {
                    yjs_driving_licence.setImg_id(img_id);//设置图片id
                    yjs_driving_licence.setAddress(driverLicenseOCRResponse.getAddress());//设置地址
                    yjs_driving_licence.setPts_id(userid);//设置当事人
                    yjs_driving_licence.setSex(driverLicenseOCRResponse.getSex());//设置性别
                    yjs_driving_licence.setCreate_time(NowDate.NowDate());//设置创建时间
                    yjs_driving_licence.setName(driverLicenseOCRResponse.getName());//设置名字
                    yjs_driving_licence.setNationality(driverLicenseOCRResponse.getNationality());//设置国际
                    yjs_driving_licence.setBirth(driverLicenseOCRResponse.getDateOfBirth());//设置生日
                    yjs_driving_licence.setFirst_date(driverLicenseOCRResponse.getDateOfFirstIssue());//设置初次领证时间
                    yjs_driving_licence.setStart_date(driverLicenseOCRResponse.getStartDate());//设置有效期开始时间
                    yjs_driving_licence.setEnd_date(driverLicenseOCRResponse.getEndDate());//设置截止时间
                    yjs_driving_licence.setClasss(driverLicenseOCRResponse.getClass_());//设置准驾车型
                    yjs_driving_licence.setCard_code(driverLicenseOCRResponse.getCardCode());//设置证件号
                    yjs_driving_licence.setAuthority(driverLicenseOCRResponse.getIssuingAuthority());//设置发证单位
                    BaseMapper<Yjs_driving_licence> baseMapper1 = yjs_driving_licenceService.getBaseMapper();
                    int insert = baseMapper1.insert(yjs_driving_licence);
                    map.put("img_id", img_id);
                    map.put("card_id", yjs_driving_licence.getDl_id());
                    map.put("pts_id",pts_id);
                }
            }catch (Exception e){
                logger.error("保存驾驶证信息失败",e.getMessage());
            }
        }else {
            Integer img_id=null;
            try {
                //保存图片
                Yjs_image yjs_image = new Yjs_image();
                yjs_image.setImg_name(name);
                yjs_image.setFlag("1");
                yjs_image.setImg_type("2");
                yjs_image.setPts_id(userid);
                yjs_image.setCreate_time(NowDate.NowDate());
                Yjs_imageMapper baseMapper = yjs_imageService.getBaseMapper();
                baseMapper.insert(yjs_image);
                 img_id = yjs_image.getImg_id();
            }catch (Exception e){
                logger.error("保存图片失败");
            }

            Yjs_driving_licence yjs_driving_licence = new Yjs_driving_licence();
           try {
               //保存驾驶证信息
               if (driverLicenseOCRResponse.getSex() == null||driverLicenseOCRResponse.getSex().equals("")) {
                   yjs_driving_licence.setArchives_code(driverLicenseOCRResponse.getArchivesCode());//设置档案编号
                   BaseMapper<Yjs_driving_licence> baseMapper1 = yjs_driving_licenceService.getBaseMapper();
                   yjs_driving_licence.setUpdate_time(NowDate.NowDate());//设置更新时间
                   QueryWrapper<Yjs_driving_licence> Wrapper = new QueryWrapper<>();
                   Wrapper.eq("pts_id",pts_id);
                   int update = baseMapper1.update(yjs_driving_licence, Wrapper);
                   map.put("id", img_id);
                   map.put("card_id", id);
                   map.put("pts_id",pts_id);
               }else {
                   yjs_driving_licence.setImg_id(img_id);//设置图片id
                   yjs_driving_licence.setAddress(driverLicenseOCRResponse.getAddress());//设置地址
                   //yjs_driving_licence.setPts_id(12);//设置当事人
                   yjs_driving_licence.setSex(driverLicenseOCRResponse.getSex());//设置性别
                   yjs_driving_licence.setUpdate_time(NowDate.NowDate());//设置创建时间
                   yjs_driving_licence.setName(driverLicenseOCRResponse.getName());//设置名字
                   yjs_driving_licence.setNationality(driverLicenseOCRResponse.getNationality());//设置国际
                   yjs_driving_licence.setBirth(driverLicenseOCRResponse.getDateOfBirth());//设置生日
                   yjs_driving_licence.setFirst_date(driverLicenseOCRResponse.getDateOfFirstIssue());//设置初次领证时间
                   yjs_driving_licence.setStart_date(driverLicenseOCRResponse.getStartDate());//设置有效期开始时间
                   yjs_driving_licence.setEnd_date(driverLicenseOCRResponse.getEndDate());//设置截止时间
                   yjs_driving_licence.setClasss(driverLicenseOCRResponse.getClass_());//设置准驾车型
                   yjs_driving_licence.setCard_code(driverLicenseOCRResponse.getCardCode());//设置证件号
                   yjs_driving_licence.setAuthority(driverLicenseOCRResponse.getIssuingAuthority());//设置发证单位
                   BaseMapper<Yjs_driving_licence> baseMapper1 = yjs_driving_licenceService.getBaseMapper();
                   QueryWrapper<Yjs_driving_licence> Wrapper = new QueryWrapper<>();
                   Wrapper.eq("pts_id",pts_id);
                   int update = baseMapper1.update(yjs_driving_licence, Wrapper);
                   map.put("id", img_id);
                   map.put("card_id", id);
                   map.put("pts_id",pts_id);
               }
           }catch (Exception e){
               logger.error("更新驾驶证信息出错",e.getMessage());
           }
        }
        return ResultBody.success(map);
    }



    /**
     * 行驶证
     * @param img
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/uploadVehicleLicense")
    @NoCheck
    public ResultBody uploadVehicleLicenseOCR(@RequestParam("pts_id") String pts_id,@RequestParam("card_id") String id,@RequestParam("img") MultipartFile img, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer userid = Integer.valueOf(pts_id);
        File file1 = MutipartFileToFile.multipartFileToFile(img);
        String base64 = MutipartFileToFile.ImgFileToBase64Str(file1);
        VehicleLicenseOCRResponse vehicleLicenseOCRResponse = OCRUtils.VehicleLicenseOCR(base64);
        if (vehicleLicenseOCRResponse==null){
            return ResultBody.error("500","识别错误");
        }
        String name = Upload.uploadImg(img, request, response);
        Map<String, Object> map = new HashMap<>();
        BaseMapper<Yjs_vehicle_licence> baseMapper1 = yjs_vehicle_licenceService.getBaseMapper();
        if (name==null){
            return ResultBody.error("500","上传文件失败");
        }
        if (id==null||id.equals("")||id.equals("null")) {
            Integer img_id=null;
            try {
                //保存图片
                Yjs_image yjs_image = new Yjs_image();
                yjs_image.setImg_name(name);
                yjs_image.setFlag("1");
                yjs_image.setImg_type("3");
                yjs_image.setPts_id(userid);
                yjs_image.setCreate_time(NowDate.NowDate());
                Yjs_imageMapper baseMapper = yjs_imageService.getBaseMapper();
                baseMapper.insert(yjs_image);
                img_id = yjs_image.getImg_id();
            }catch (Exception e){
                logger.error("保存行驶证图片出错",e.getMessage());
            }
            Yjs_vehicle_licence yjs_vehicle_licence = new Yjs_vehicle_licence();
            try {

                if (vehicleLicenseOCRResponse.getFrontInfo()==null){
                    //反面
                    yjs_vehicle_licence.setPts_id(userid);//设置当事人id
                    yjs_vehicle_licence.setFile_no(vehicleLicenseOCRResponse.getBackInfo().getFileNo());//设置档案编号
                    yjs_vehicle_licence.setAllow_num(vehicleLicenseOCRResponse.getBackInfo().getAllowNum());//设置核定人数
                    yjs_vehicle_licence.setTotal_mass(vehicleLicenseOCRResponse.getBackInfo().getTotalMass());//设置总质量
                    yjs_vehicle_licence.setCurb_weight(vehicleLicenseOCRResponse.getBackInfo().getCurbWeight());//整备质量
                    yjs_vehicle_licence.setLoad_quality(vehicleLicenseOCRResponse.getBackInfo().getLoadQuality());//核定载质量
                    yjs_vehicle_licence.setExternal_size(vehicleLicenseOCRResponse.getBackInfo().getExternalSize());//外廓尺寸
                    yjs_vehicle_licence.setTotal_mass_norm(vehicleLicenseOCRResponse.getBackInfo().getTotalQuasiMass());//准牵引总质量
                    yjs_vehicle_licence.setMarks(vehicleLicenseOCRResponse.getBackInfo().getMarks());//备注
                    yjs_vehicle_licence.setRecord(vehicleLicenseOCRResponse.getBackInfo().getRecord());//检验记录
                    yjs_vehicle_licence.setCreate_time(NowDate.NowDate());//设置创建时间
                    int insert = baseMapper1.insert(yjs_vehicle_licence);
                    map.put("id", img_id);
                    map.put("card_id", yjs_vehicle_licence.getVl_id());
                    map.put("pts_id",pts_id);
                }else{
                    //正面
                    yjs_vehicle_licence.setImg_id(img_id);//设置图片id
                    yjs_vehicle_licence.setPlate_no(vehicleLicenseOCRResponse.getFrontInfo().getPlateNo());//设置车牌号
                    yjs_vehicle_licence.setPts_id(userid);//设置当事人id
                    yjs_vehicle_licence.setAddress(vehicleLicenseOCRResponse.getFrontInfo().getAddress());//设置地址
                    yjs_vehicle_licence.setVehicle_type(vehicleLicenseOCRResponse.getFrontInfo().getVehicleType());//设置轿车类型
                    yjs_vehicle_licence.setOwner(vehicleLicenseOCRResponse.getFrontInfo().getOwner());//设置姓名
                    yjs_vehicle_licence.setUse_character(vehicleLicenseOCRResponse.getFrontInfo().getUseCharacter());//使用性质
                    yjs_vehicle_licence.setModel(vehicleLicenseOCRResponse.getFrontInfo().getModel());//品牌型号
                    yjs_vehicle_licence.setVin(vehicleLicenseOCRResponse.getFrontInfo().getVin());//车辆识别代号
                    yjs_vehicle_licence.setEngine_no(vehicleLicenseOCRResponse.getFrontInfo().getEngineNo());//发动机号码
                    yjs_vehicle_licence.setRegister_date(vehicleLicenseOCRResponse.getFrontInfo().getRegisterDate());//注册日期
                    yjs_vehicle_licence.setIssue_date(vehicleLicenseOCRResponse.getFrontInfo().getIssueDate());//发证日期
                    yjs_vehicle_licence.setSeal(vehicleLicenseOCRResponse.getFrontInfo().getSeal());//印章
                    yjs_vehicle_licence.setCreate_time(NowDate.NowDate());//设置创建时间
                    int insert = baseMapper1.insert(yjs_vehicle_licence);
                    map.put("id", img_id);
                    map.put("card_id", yjs_vehicle_licence.getVl_id());
                    map.put("pts_id",pts_id);
                }
            }catch (Exception e){
                logger.error("保存行驶证信息出错",e.getMessage());
            }
        }else {
            Integer img_id=null;
            QueryWrapper<Yjs_vehicle_licence> Wrapper = new QueryWrapper<>();
            try {
                //保存图片
                Yjs_image yjs_image = new Yjs_image();
                yjs_image.setImg_name(name);
                yjs_image.setFlag("1");
                yjs_image.setImg_type("3");
                yjs_image.setPts_id(userid);
                yjs_image.setCreate_time(NowDate.NowDate());
                Yjs_imageMapper baseMapper = yjs_imageService.getBaseMapper();
                baseMapper.insert(yjs_image);
                img_id = yjs_image.getImg_id();
            }catch (Exception e){
                logger.error("报错行驶证图片失败");
            }
            Yjs_vehicle_licence yjs_vehicle_licence = new Yjs_vehicle_licence();
            try {
                if (vehicleLicenseOCRResponse.getFrontInfo()==null){
                    //反面
                    yjs_vehicle_licence.setFile_no(vehicleLicenseOCRResponse.getBackInfo().getFileNo());//设置档案编号
                    yjs_vehicle_licence.setAllow_num(vehicleLicenseOCRResponse.getBackInfo().getAllowNum());//设置核定人数
                    yjs_vehicle_licence.setTotal_mass(vehicleLicenseOCRResponse.getBackInfo().getTotalMass());//设置总质量
                    yjs_vehicle_licence.setCurb_weight(vehicleLicenseOCRResponse.getBackInfo().getCurbWeight());//整备质量
                    yjs_vehicle_licence.setLoad_quality(vehicleLicenseOCRResponse.getBackInfo().getLoadQuality());//核定载质量
                    yjs_vehicle_licence.setExternal_size(vehicleLicenseOCRResponse.getBackInfo().getExternalSize());//外廓尺寸
                    yjs_vehicle_licence.setTotal_mass_norm(vehicleLicenseOCRResponse.getBackInfo().getTotalQuasiMass());//准牵引总质量
                    yjs_vehicle_licence.setMarks(vehicleLicenseOCRResponse.getBackInfo().getMarks());//备注
                    yjs_vehicle_licence.setRecord(vehicleLicenseOCRResponse.getBackInfo().getRecord());//检验记录
                    yjs_vehicle_licence.setUpdate_time(NowDate.NowDate());//设置创建时间
                    Wrapper.eq("pts_id",pts_id);
                    int update = baseMapper1.update(yjs_vehicle_licence, Wrapper);
                    map.put("id", img_id);
                    map.put("card_id", id);
                    map.put("pts_id",pts_id);
                }else{
                    //正面
                    yjs_vehicle_licence.setImg_id(img_id);//设置图片id
                    yjs_vehicle_licence.setPlate_no(vehicleLicenseOCRResponse.getFrontInfo().getPlateNo());//设置车牌号
                    yjs_vehicle_licence.setAddress(vehicleLicenseOCRResponse.getFrontInfo().getAddress());//设置地址
                    yjs_vehicle_licence.setVehicle_type(vehicleLicenseOCRResponse.getFrontInfo().getVehicleType());//设置轿车类型
                    yjs_vehicle_licence.setOwner(vehicleLicenseOCRResponse.getFrontInfo().getOwner());//设置姓名
                    yjs_vehicle_licence.setUse_character(vehicleLicenseOCRResponse.getFrontInfo().getUseCharacter());//使用性质
                    yjs_vehicle_licence.setModel(vehicleLicenseOCRResponse.getFrontInfo().getModel());//品牌型号
                    yjs_vehicle_licence.setVin(vehicleLicenseOCRResponse.getFrontInfo().getVin());//车辆识别代号
                    yjs_vehicle_licence.setEngine_no(vehicleLicenseOCRResponse.getFrontInfo().getEngineNo());//发动机号码
                    yjs_vehicle_licence.setRegister_date(vehicleLicenseOCRResponse.getFrontInfo().getRegisterDate());//注册日期
                    yjs_vehicle_licence.setIssue_date(vehicleLicenseOCRResponse.getFrontInfo().getIssueDate());//发证日期
                    yjs_vehicle_licence.setSeal(vehicleLicenseOCRResponse.getFrontInfo().getSeal());//印章
                    yjs_vehicle_licence.setUpdate_time(NowDate.NowDate());//设置创建时间
                    Wrapper.eq("pts_id",pts_id);
                    int update = baseMapper1.update(yjs_vehicle_licence, Wrapper);
                    map.put("id", img_id);
                    map.put("card_id", id);
                    map.put("pts_id",pts_id);
                }
            }catch (Exception e){
                logger.error("更新行驶证图片失败",e.getMessage());
            }
        }
       return ResultBody.success(map);
    }


    /**
     * 车牌号
     * @param img
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/uploadLicensePlate")
    @NoCheck
    public ResultBody uploadLicensePlateOCR(@RequestParam("img") MultipartFile img, HttpServletRequest request, HttpServletResponse response) throws Exception {
        File file1 = MutipartFileToFile.multipartFileToFile(img);
        String base64 = MutipartFileToFile.ImgFileToBase64Str(file1);
        LicensePlateOCRResponse licensePlateOCRResponse = OCRUtils.LicensePlateOCR(base64);
        String name = Upload.uploadImg(img, request, response);
        if (name==null){
            return ResultBody.error("500","上传文件失败");
        }
        Yjs_image yjs_image = new Yjs_image();
        yjs_image.setImg_name(name);
        yjs_image.setFlag("1");
        yjs_image.setImg_type("车牌号");
        yjs_image.setPts_id(12);
        yjs_image.setCreate_time(NowDate.NowDate());
        Yjs_imageMapper baseMapper = yjs_imageService.getBaseMapper();
        baseMapper.insert(yjs_image);
        Integer img_id = yjs_image.getImg_id();
        Map<String, Object> map = new HashMap<>();
        map.put("id",img_id);
        map.put("list",licensePlateOCRResponse);
        return ResultBody.success(map);
    }

    /**
     * 保险单照片
     * @param img
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/uploadinsurance")
    @NoCheck
    public ResultBody uplodinsurance(@RequestParam("img") MultipartFile img, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = Upload.uploadImg(img, request, response);
        if (name==null){
            return ResultBody.error("500","上传文件失败");
        }
        Yjs_image yjs_image = new Yjs_image();
        yjs_image.setImg_name(name);
        yjs_image.setFlag("1");
        yjs_image.setImg_type("4");
        yjs_image.setPts_id(12);
        yjs_image.setCreate_time(NowDate.NowDate());
        Yjs_imageMapper baseMapper = yjs_imageService.getBaseMapper();
        baseMapper.insert(yjs_image);
        Integer img_id = yjs_image.getImg_id();
        Map<String, Object> map = new HashMap<>();
        map.put("mg","上传成功");
        map.put("id",img_id);
        return ResultBody.success(map);
    }

    /**
     * 单张照片上传
     * @param img
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/upload")
    @NoCheck
    public ResultBody uplod(@RequestParam("img") MultipartFile img, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = Upload.uploadImg(img, request, response);
        if (name==null){
            return ResultBody.error("500","上传文件失败");
        }
        Yjs_image yjs_image = new Yjs_image();
        yjs_image.setImg_name(name);
        yjs_image.setFlag("1");
        yjs_image.setImg_type("5");
        yjs_image.setPts_id(12);
        yjs_image.setCreate_time(NowDate.NowDate());
        Yjs_imageMapper baseMapper = yjs_imageService.getBaseMapper();
        baseMapper.insert(yjs_image);
        Integer img_id = yjs_image.getImg_id();
        Map<String, Object> map = new HashMap<>();
        map.put("mg","上传成功");
        map.put("id",img_id);
        return ResultBody.success(map);
    }

    /**
     * 查询当前人上传的照片
     * @param pts_id
     * @return
     */
    @ResponseBody
    @PostMapping("/findcard")
    @NoCheck
    public ResultBody selectIdcard(String pts_id,String img_type){
        Integer integer = Integer.valueOf(pts_id);
        Yjs_imageMapper baseMapper = yjs_imageService.getBaseMapper();
        QueryWrapper<Yjs_image> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("img_type",img_type);
        QueryWrapper.eq("flag","1");
        QueryWrapper.eq("pts_id",12);
        List<Yjs_image> yjs_images = baseMapper.selectList(QueryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("list",yjs_images);
        return ResultBody.success(map);
    }


}
