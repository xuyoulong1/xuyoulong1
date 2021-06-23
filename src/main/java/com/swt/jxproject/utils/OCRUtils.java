package com.swt.jxproject.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.*;

import java.io.IOException;

public class OCRUtils {
    /**
     * 身份证识别
     * @param base64
     * @return
     */
    public static IDCardOCRResponse  IDCardOCR(String base64){
        IDCardOCRResponse resp=null;
        try{
             Credential cred = new Credential("AKID97pIVR6VdU3LN8droUu2uZx4uXswjGWV", "jCOJxKQGq9BxSgfOqWzC8nBI9j24oHTO");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("ocr.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            OcrClient client = new OcrClient(cred, "ap-guangzhou", clientProfile);

            IDCardOCRRequest req = new IDCardOCRRequest();
//            req.setImageUrl("http://47.118.74.196:8080/a/7711.jpg");
//            File file = FileUtil.file("http://47.118.74.196:8080/a/io.jpg");
            req.setImageBase64(base64);

             resp = client.IDCardOCR(req);

            System.out.println(IDCardOCRResponse.toJsonString(resp));

        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
            return null;
        }
        return resp;
    }

    /**
     * 驾驶证识别
     */
    public static  DriverLicenseOCRResponse DriverLicenseOCR(String base64){
        DriverLicenseOCRResponse resp=null;
        try{
            Credential cred = new Credential("AKID97pIVR6VdU3LN8droUu2uZx4uXswjGWV", "jCOJxKQGq9BxSgfOqWzC8nBI9j24oHTO");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("ocr.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            OcrClient client = new OcrClient(cred, "ap-guangzhou", clientProfile);

            DriverLicenseOCRRequest req = new DriverLicenseOCRRequest();

            req.setImageBase64(base64);

             resp = client.DriverLicenseOCR(req);

            System.out.println(DriverLicenseOCRResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
            return null;
        }
        return resp;
    }

    /**
     * 行驶证识别
     * @param base64
     * @return
     */
    public static VehicleLicenseOCRResponse VehicleLicenseOCR(String base64){
        VehicleLicenseOCRResponse resp=null;
        try{
            Credential cred = new Credential("AKID97pIVR6VdU3LN8droUu2uZx4uXswjGWV", "jCOJxKQGq9BxSgfOqWzC8nBI9j24oHTO");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("ocr.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            OcrClient client = new OcrClient(cred, "ap-guangzhou", clientProfile);

            VehicleLicenseOCRRequest req = new VehicleLicenseOCRRequest();

            req.setImageBase64(base64);

             resp = client.VehicleLicenseOCR(req);

            System.out.println(VehicleLicenseOCRResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
            return null;
        }
        return resp;
    }

    /**
     * 车牌号识别
     * @param base64
     * @return
     */
    public static LicensePlateOCRResponse  LicensePlateOCR(String base64){
        try{
            Credential cred = new Credential("SecretId", "SecretKey");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("ocr.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            OcrClient client = new OcrClient(cred, "", clientProfile);

            LicensePlateOCRRequest req = new LicensePlateOCRRequest();

            req.setImageBase64(base64);

            LicensePlateOCRResponse resp = client.LicensePlateOCR(req);

            System.out.println(LicensePlateOCRResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
