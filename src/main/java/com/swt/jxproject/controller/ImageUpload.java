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
     * ???????????????
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
            return ResultBody.error("500","????????????");
        }
        String name = Upload.uploadImg(img, request, response);
        if (name==null){
            return ResultBody.error("500","??????????????????");
        }
        Map<String, Object> map = new HashMap<>();
        if(id==null|| id.equals("")||id.equals("null")) {
            Integer img_id=null;
            try {
                //????????????
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
                logger.error("???????????????????????????",e.getMessage());
            }
            Yjs_ident_card yjs_ident_card = new Yjs_ident_card();
           try {
               //?????????????????????
               if (idCardOCRResponse.getSex() == null||idCardOCRResponse.getSex().equals("")) {
                   yjs_ident_card.setPts_id(userid);//???????????????
                   yjs_ident_card.setCreate_time(NowDate.NowDate());//??????????????????
                   yjs_ident_card.setAuthority(idCardOCRResponse.getAuthority());//??????????????????
                   yjs_ident_card.setValid_date(idCardOCRResponse.getValidDate());//???????????????
               } else {
                   yjs_ident_card.setImg_id(img_id);//????????????id
                   yjs_ident_card.setPts_id(userid);//???????????????
                   yjs_ident_card.setAddress(idCardOCRResponse.getAddress());//????????????
                   yjs_ident_card.setCreate_time(NowDate.NowDate());//???????????????
                   yjs_ident_card.setBirth(idCardOCRResponse.getBirth());//????????????
                   yjs_ident_card.setName(idCardOCRResponse.getName());//????????????
                   yjs_ident_card.setCard_num(idCardOCRResponse.getIdNum());//??????????????????
                   yjs_ident_card.setSex(idCardOCRResponse.getSex());//????????????
                   yjs_ident_card.setNation(idCardOCRResponse.getNation());//????????????
               }
               BaseMapper<Yjs_ident_card> baseMapper1 = yjs_ident_cardService.getBaseMapper();
               int insert1 = baseMapper1.insert(yjs_ident_card);
               map.put("img_id", img_id);
               map.put("card_id", yjs_ident_card.getIc_id());
               map.put("pts_id", pts_id);
           }catch (Exception e){
               logger.error("?????????????????????????????????",e.getMessage());
           }
        }else{
            Integer img_id=null;
            try {
                //????????????
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
                logger.error("???????????????????????????",e.getMessage());
            }
            Yjs_ident_card yjs_ident_card = new Yjs_ident_card();
            try {
                //?????????????????????
                if (idCardOCRResponse.getSex() == null||idCardOCRResponse.getSex().equals("")) {
                    yjs_ident_card.setUpdate_time(NowDate.NowDate());//????????????
                    yjs_ident_card.setAuthority(idCardOCRResponse.getAuthority());//??????????????????
                    yjs_ident_card.setPts_id(userid);//???????????????id
                    yjs_ident_card.setValid_date(idCardOCRResponse.getValidDate());//???????????????
                } else {
                    yjs_ident_card.setImg_id(img_id);//????????????id
                    yjs_ident_card.setPts_id(userid);//???????????????id
                    yjs_ident_card.setAddress(idCardOCRResponse.getAddress());//????????????
                    yjs_ident_card.setUpdate_time(NowDate.NowDate());//????????????
                    yjs_ident_card.setBirth(idCardOCRResponse.getBirth());//????????????
                    yjs_ident_card.setName(idCardOCRResponse.getName());//????????????
                    yjs_ident_card.setCard_num(idCardOCRResponse.getIdNum());//??????????????????
                    yjs_ident_card.setSex(idCardOCRResponse.getSex());//????????????
                    yjs_ident_card.setNation(idCardOCRResponse.getNation());//????????????
                }
                BaseMapper<Yjs_ident_card> baseMapper1 = yjs_ident_cardService.getBaseMapper();
                QueryWrapper<Yjs_ident_card> Query = new QueryWrapper<>();
                Query.eq("pts_id",pts_id);
                int update = baseMapper1.update(yjs_ident_card, Query);
                map.put("img_id", img_id);
                map.put("card_id", id);
                map.put("pts_id",pts_id);
            }catch (Exception e){
                logger.error("???????????????????????????",e.getMessage());
            }
        }
        return ResultBody.success(map);
    }

    /**
     * ??????????????????
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
     * ???????????????
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
            return ResultBody.error("500","????????????");
        }
        String name = Upload.uploadImg(img, request, response);
        Map<String, Object> map = new HashMap<>();
        if (name == null) {
            return ResultBody.error("500", "??????????????????");
        }
        if (id == null || id.equals("")||id.equals("null")) {
            Integer img_id=null;
            try {
                //????????????
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
                logger.error("???????????????????????????",e.getMessage());
            }
            Yjs_driving_licence yjs_driving_licence = new Yjs_driving_licence();
            try {
                //?????????????????????
                if (driverLicenseOCRResponse.getSex() == null||driverLicenseOCRResponse.getSex().equals("")) {
                    yjs_driving_licence.setPts_id(userid);//???????????????
                    yjs_driving_licence.setArchives_code(driverLicenseOCRResponse.getArchivesCode());//??????????????????
                    BaseMapper<Yjs_driving_licence> baseMapper1 = yjs_driving_licenceService.getBaseMapper();
                    int insert = baseMapper1.insert(yjs_driving_licence);
                    map.put("img_id", img_id);
                    map.put("pts_id",pts_id);
                    map.put("card_id", yjs_driving_licence.getDl_id());
                }else {
                    yjs_driving_licence.setImg_id(img_id);//????????????id
                    yjs_driving_licence.setAddress(driverLicenseOCRResponse.getAddress());//????????????
                    yjs_driving_licence.setPts_id(userid);//???????????????
                    yjs_driving_licence.setSex(driverLicenseOCRResponse.getSex());//????????????
                    yjs_driving_licence.setCreate_time(NowDate.NowDate());//??????????????????
                    yjs_driving_licence.setName(driverLicenseOCRResponse.getName());//????????????
                    yjs_driving_licence.setNationality(driverLicenseOCRResponse.getNationality());//????????????
                    yjs_driving_licence.setBirth(driverLicenseOCRResponse.getDateOfBirth());//????????????
                    yjs_driving_licence.setFirst_date(driverLicenseOCRResponse.getDateOfFirstIssue());//????????????????????????
                    yjs_driving_licence.setStart_date(driverLicenseOCRResponse.getStartDate());//???????????????????????????
                    yjs_driving_licence.setEnd_date(driverLicenseOCRResponse.getEndDate());//??????????????????
                    yjs_driving_licence.setClasss(driverLicenseOCRResponse.getClass_());//??????????????????
                    yjs_driving_licence.setCard_code(driverLicenseOCRResponse.getCardCode());//???????????????
                    yjs_driving_licence.setAuthority(driverLicenseOCRResponse.getIssuingAuthority());//??????????????????
                    BaseMapper<Yjs_driving_licence> baseMapper1 = yjs_driving_licenceService.getBaseMapper();
                    int insert = baseMapper1.insert(yjs_driving_licence);
                    map.put("img_id", img_id);
                    map.put("card_id", yjs_driving_licence.getDl_id());
                    map.put("pts_id",pts_id);
                }
            }catch (Exception e){
                logger.error("???????????????????????????",e.getMessage());
            }
        }else {
            Integer img_id=null;
            try {
                //????????????
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
                logger.error("??????????????????");
            }

            Yjs_driving_licence yjs_driving_licence = new Yjs_driving_licence();
           try {
               //?????????????????????
               if (driverLicenseOCRResponse.getSex() == null||driverLicenseOCRResponse.getSex().equals("")) {
                   yjs_driving_licence.setArchives_code(driverLicenseOCRResponse.getArchivesCode());//??????????????????
                   BaseMapper<Yjs_driving_licence> baseMapper1 = yjs_driving_licenceService.getBaseMapper();
                   yjs_driving_licence.setUpdate_time(NowDate.NowDate());//??????????????????
                   QueryWrapper<Yjs_driving_licence> Wrapper = new QueryWrapper<>();
                   Wrapper.eq("pts_id",pts_id);
                   int update = baseMapper1.update(yjs_driving_licence, Wrapper);
                   map.put("id", img_id);
                   map.put("card_id", id);
                   map.put("pts_id",pts_id);
               }else {
                   yjs_driving_licence.setImg_id(img_id);//????????????id
                   yjs_driving_licence.setAddress(driverLicenseOCRResponse.getAddress());//????????????
                   //yjs_driving_licence.setPts_id(12);//???????????????
                   yjs_driving_licence.setSex(driverLicenseOCRResponse.getSex());//????????????
                   yjs_driving_licence.setUpdate_time(NowDate.NowDate());//??????????????????
                   yjs_driving_licence.setName(driverLicenseOCRResponse.getName());//????????????
                   yjs_driving_licence.setNationality(driverLicenseOCRResponse.getNationality());//????????????
                   yjs_driving_licence.setBirth(driverLicenseOCRResponse.getDateOfBirth());//????????????
                   yjs_driving_licence.setFirst_date(driverLicenseOCRResponse.getDateOfFirstIssue());//????????????????????????
                   yjs_driving_licence.setStart_date(driverLicenseOCRResponse.getStartDate());//???????????????????????????
                   yjs_driving_licence.setEnd_date(driverLicenseOCRResponse.getEndDate());//??????????????????
                   yjs_driving_licence.setClasss(driverLicenseOCRResponse.getClass_());//??????????????????
                   yjs_driving_licence.setCard_code(driverLicenseOCRResponse.getCardCode());//???????????????
                   yjs_driving_licence.setAuthority(driverLicenseOCRResponse.getIssuingAuthority());//??????????????????
                   BaseMapper<Yjs_driving_licence> baseMapper1 = yjs_driving_licenceService.getBaseMapper();
                   QueryWrapper<Yjs_driving_licence> Wrapper = new QueryWrapper<>();
                   Wrapper.eq("pts_id",pts_id);
                   int update = baseMapper1.update(yjs_driving_licence, Wrapper);
                   map.put("id", img_id);
                   map.put("card_id", id);
                   map.put("pts_id",pts_id);
               }
           }catch (Exception e){
               logger.error("???????????????????????????",e.getMessage());
           }
        }
        return ResultBody.success(map);
    }



    /**
     * ?????????
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
            return ResultBody.error("500","????????????");
        }
        String name = Upload.uploadImg(img, request, response);
        Map<String, Object> map = new HashMap<>();
        BaseMapper<Yjs_vehicle_licence> baseMapper1 = yjs_vehicle_licenceService.getBaseMapper();
        if (name==null){
            return ResultBody.error("500","??????????????????");
        }
        if (id==null||id.equals("")||id.equals("null")) {
            Integer img_id=null;
            try {
                //????????????
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
                logger.error("???????????????????????????",e.getMessage());
            }
            Yjs_vehicle_licence yjs_vehicle_licence = new Yjs_vehicle_licence();
            try {

                if (vehicleLicenseOCRResponse.getFrontInfo()==null){
                    //??????
                    yjs_vehicle_licence.setPts_id(userid);//???????????????id
                    yjs_vehicle_licence.setFile_no(vehicleLicenseOCRResponse.getBackInfo().getFileNo());//??????????????????
                    yjs_vehicle_licence.setAllow_num(vehicleLicenseOCRResponse.getBackInfo().getAllowNum());//??????????????????
                    yjs_vehicle_licence.setTotal_mass(vehicleLicenseOCRResponse.getBackInfo().getTotalMass());//???????????????
                    yjs_vehicle_licence.setCurb_weight(vehicleLicenseOCRResponse.getBackInfo().getCurbWeight());//????????????
                    yjs_vehicle_licence.setLoad_quality(vehicleLicenseOCRResponse.getBackInfo().getLoadQuality());//???????????????
                    yjs_vehicle_licence.setExternal_size(vehicleLicenseOCRResponse.getBackInfo().getExternalSize());//????????????
                    yjs_vehicle_licence.setTotal_mass_norm(vehicleLicenseOCRResponse.getBackInfo().getTotalQuasiMass());//??????????????????
                    yjs_vehicle_licence.setMarks(vehicleLicenseOCRResponse.getBackInfo().getMarks());//??????
                    yjs_vehicle_licence.setRecord(vehicleLicenseOCRResponse.getBackInfo().getRecord());//????????????
                    yjs_vehicle_licence.setCreate_time(NowDate.NowDate());//??????????????????
                    int insert = baseMapper1.insert(yjs_vehicle_licence);
                    map.put("id", img_id);
                    map.put("card_id", yjs_vehicle_licence.getVl_id());
                    map.put("pts_id",pts_id);
                }else{
                    //??????
                    yjs_vehicle_licence.setImg_id(img_id);//????????????id
                    yjs_vehicle_licence.setPlate_no(vehicleLicenseOCRResponse.getFrontInfo().getPlateNo());//???????????????
                    yjs_vehicle_licence.setPts_id(userid);//???????????????id
                    yjs_vehicle_licence.setAddress(vehicleLicenseOCRResponse.getFrontInfo().getAddress());//????????????
                    yjs_vehicle_licence.setVehicle_type(vehicleLicenseOCRResponse.getFrontInfo().getVehicleType());//??????????????????
                    yjs_vehicle_licence.setOwner(vehicleLicenseOCRResponse.getFrontInfo().getOwner());//????????????
                    yjs_vehicle_licence.setUse_character(vehicleLicenseOCRResponse.getFrontInfo().getUseCharacter());//????????????
                    yjs_vehicle_licence.setModel(vehicleLicenseOCRResponse.getFrontInfo().getModel());//????????????
                    yjs_vehicle_licence.setVin(vehicleLicenseOCRResponse.getFrontInfo().getVin());//??????????????????
                    yjs_vehicle_licence.setEngine_no(vehicleLicenseOCRResponse.getFrontInfo().getEngineNo());//???????????????
                    yjs_vehicle_licence.setRegister_date(vehicleLicenseOCRResponse.getFrontInfo().getRegisterDate());//????????????
                    yjs_vehicle_licence.setIssue_date(vehicleLicenseOCRResponse.getFrontInfo().getIssueDate());//????????????
                    yjs_vehicle_licence.setSeal(vehicleLicenseOCRResponse.getFrontInfo().getSeal());//??????
                    yjs_vehicle_licence.setCreate_time(NowDate.NowDate());//??????????????????
                    int insert = baseMapper1.insert(yjs_vehicle_licence);
                    map.put("id", img_id);
                    map.put("card_id", yjs_vehicle_licence.getVl_id());
                    map.put("pts_id",pts_id);
                }
            }catch (Exception e){
                logger.error("???????????????????????????",e.getMessage());
            }
        }else {
            Integer img_id=null;
            QueryWrapper<Yjs_vehicle_licence> Wrapper = new QueryWrapper<>();
            try {
                //????????????
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
                logger.error("???????????????????????????");
            }
            Yjs_vehicle_licence yjs_vehicle_licence = new Yjs_vehicle_licence();
            try {
                if (vehicleLicenseOCRResponse.getFrontInfo()==null){
                    //??????
                    yjs_vehicle_licence.setFile_no(vehicleLicenseOCRResponse.getBackInfo().getFileNo());//??????????????????
                    yjs_vehicle_licence.setAllow_num(vehicleLicenseOCRResponse.getBackInfo().getAllowNum());//??????????????????
                    yjs_vehicle_licence.setTotal_mass(vehicleLicenseOCRResponse.getBackInfo().getTotalMass());//???????????????
                    yjs_vehicle_licence.setCurb_weight(vehicleLicenseOCRResponse.getBackInfo().getCurbWeight());//????????????
                    yjs_vehicle_licence.setLoad_quality(vehicleLicenseOCRResponse.getBackInfo().getLoadQuality());//???????????????
                    yjs_vehicle_licence.setExternal_size(vehicleLicenseOCRResponse.getBackInfo().getExternalSize());//????????????
                    yjs_vehicle_licence.setTotal_mass_norm(vehicleLicenseOCRResponse.getBackInfo().getTotalQuasiMass());//??????????????????
                    yjs_vehicle_licence.setMarks(vehicleLicenseOCRResponse.getBackInfo().getMarks());//??????
                    yjs_vehicle_licence.setRecord(vehicleLicenseOCRResponse.getBackInfo().getRecord());//????????????
                    yjs_vehicle_licence.setUpdate_time(NowDate.NowDate());//??????????????????
                    Wrapper.eq("pts_id",pts_id);
                    int update = baseMapper1.update(yjs_vehicle_licence, Wrapper);
                    map.put("id", img_id);
                    map.put("card_id", id);
                    map.put("pts_id",pts_id);
                }else{
                    //??????
                    yjs_vehicle_licence.setImg_id(img_id);//????????????id
                    yjs_vehicle_licence.setPlate_no(vehicleLicenseOCRResponse.getFrontInfo().getPlateNo());//???????????????
                    yjs_vehicle_licence.setAddress(vehicleLicenseOCRResponse.getFrontInfo().getAddress());//????????????
                    yjs_vehicle_licence.setVehicle_type(vehicleLicenseOCRResponse.getFrontInfo().getVehicleType());//??????????????????
                    yjs_vehicle_licence.setOwner(vehicleLicenseOCRResponse.getFrontInfo().getOwner());//????????????
                    yjs_vehicle_licence.setUse_character(vehicleLicenseOCRResponse.getFrontInfo().getUseCharacter());//????????????
                    yjs_vehicle_licence.setModel(vehicleLicenseOCRResponse.getFrontInfo().getModel());//????????????
                    yjs_vehicle_licence.setVin(vehicleLicenseOCRResponse.getFrontInfo().getVin());//??????????????????
                    yjs_vehicle_licence.setEngine_no(vehicleLicenseOCRResponse.getFrontInfo().getEngineNo());//???????????????
                    yjs_vehicle_licence.setRegister_date(vehicleLicenseOCRResponse.getFrontInfo().getRegisterDate());//????????????
                    yjs_vehicle_licence.setIssue_date(vehicleLicenseOCRResponse.getFrontInfo().getIssueDate());//????????????
                    yjs_vehicle_licence.setSeal(vehicleLicenseOCRResponse.getFrontInfo().getSeal());//??????
                    yjs_vehicle_licence.setUpdate_time(NowDate.NowDate());//??????????????????
                    Wrapper.eq("pts_id",pts_id);
                    int update = baseMapper1.update(yjs_vehicle_licence, Wrapper);
                    map.put("id", img_id);
                    map.put("card_id", id);
                    map.put("pts_id",pts_id);
                }
            }catch (Exception e){
                logger.error("???????????????????????????",e.getMessage());
            }
        }
       return ResultBody.success(map);
    }


    /**
     * ?????????
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
            return ResultBody.error("500","??????????????????");
        }
        Yjs_image yjs_image = new Yjs_image();
        yjs_image.setImg_name(name);
        yjs_image.setFlag("1");
        yjs_image.setImg_type("?????????");
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
     * ???????????????
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
            return ResultBody.error("500","??????????????????");
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
        map.put("mg","????????????");
        map.put("id",img_id);
        return ResultBody.success(map);
    }

    /**
     * ??????????????????
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
            return ResultBody.error("500","??????????????????");
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
        map.put("mg","????????????");
        map.put("id",img_id);
        return ResultBody.success(map);
    }

    /**
     * ??????????????????????????????
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
