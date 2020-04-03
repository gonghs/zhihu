package com.maple.utils;

import com.maple.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by TYZ034 on 2018/3/13.
 */
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private final static String fileDir= "D:/imageUpload/";
    public static String saveFile(MultipartFile multipartFile) {
        String newFileName = null;
        try {
            File file = new File(fileDir);
            if (!file.exists()) {
                file.mkdir();
            }
            String filename = multipartFile.getOriginalFilename();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            newFileName = fileDir + sdf.format(new Date()) + filename;
            file = new File(newFileName);
            file.createNewFile();
            InputStream stream = multipartFile.getInputStream();
            FileOutputStream fs=new FileOutputStream( file);
            byte[] buffer =new byte[1024*1024];
            int bytesum = 0;
            int byteread = 0;
            while ((byteread=stream.read(buffer))!=-1)
            {
                bytesum+=byteread;
                fs.write(buffer,0,byteread);
                fs.flush();
            }
            fs.close();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("io error");
        }
        return newFileName;
    }

    public static String base64String2Image(String imgStr) {
        File file = new File(fileDir);
        if (!file.exists()) {
            file.mkdir();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String  newFileName = fileDir + sdf.format(new Date()) + ".png";
        if (imgStr == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(newFileName);
            out.write(b);
            out.flush();
            out.close();
            return newFileName;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<FileBody> getFileList(){
        List<FileBody> rtlist = new ArrayList<>();
        File file = new File(fileDir);
        if(file.listFiles().length>0){
            for (File file1:file.listFiles()) {
                FileBody filebody = new FileBody();
                filebody.setUrl("http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png");
                filebody.setFilename("xxx");
                rtlist.add(filebody);
            }
        }
        return rtlist;
    }
}
