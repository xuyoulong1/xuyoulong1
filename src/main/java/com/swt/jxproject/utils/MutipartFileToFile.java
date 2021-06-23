package com.swt.jxproject.utils;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.io.IoUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class MutipartFileToFile {
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String ImgFileToBase64Str(File f){
        ByteArrayOutputStream bos = null;
        FileInputStream fis = null;
        String encode=null;
        try {
            bos =new ByteArrayOutputStream();
            fis = new FileInputStream(f);
            long copy = IoUtil.copy(fis, bos);
            byte[] bytes = bos.toByteArray();
            encode = Base64Encoder.encode(bytes);
            return encode;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            try {
                bos.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return encode;
    }
}
