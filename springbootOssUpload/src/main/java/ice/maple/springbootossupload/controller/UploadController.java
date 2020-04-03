package ice.maple.springbootossupload.controller;


import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import ice.maple.springbootossupload.model.Attach;
import ice.maple.springbootossupload.service.IAttachService;
import ice.maple.springbootossupload.util.UploadParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class UploadController {
    //交给spring注入参数并管理
    @Autowired
    UploadParam uploadParam;
    @Autowired
    IAttachService attachService;
    //获取上传签名
    @PostMapping("/upload/getPolicy")
    public JSONObject getPolicy(@RequestBody JSONObject jsonObject){
        JSONObject returnObj = new JSONObject();
        //文件夹名由前端指定
        String dir = jsonObject.getString("oosDirectory");
        returnObj.put("ossUrl",uploadParam.getOssUrlPrefix());
        returnObj.put("policy",getPolicy(dir));
        return returnObj;
    }

    //阿里云上传回调
    @PostMapping("/upload/uploadCallback")
    public JSONObject uploadCallback(HttpServletRequest request) {
        JSONObject returnObj = new JSONObject();
        String filename = request.getParameter("filename");
        String size = request.getParameter("size");
        //判断阿里云是不是正确接收到了文件
        if(Integer.valueOf(size)>0){
            String uploadUrl = uploadParam.getOssUrlPrefix();
            //将文件信息存入阿里云
            attachService.insert(new Attach(filename));
            returnObj.put("uploadUrl", uploadUrl+"/"+filename);
            return returnObj;
        }else{
            return null;
        }
    }



    /**
     * 签名生成
     *
     * @return
     */
    private JSONObject getPolicy(String dir) {
//        uploadParam.setCallbackUrl("http://gonghs.free.ngrok.cc/web-portal/pub/comm/uploadCallback");
        OSSClient aliyunOssClient = uploadParam.getOssClient();

        JSONObject result = new JSONObject();
        // 存储目录
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (StringUtils.isEmpty(dir)) {
            dir = sdf.format(new Date());
        }

        // 签名有效期
        long expireEndTime = System.currentTimeMillis() + 300 * 1000;
        Date expiration = new Date(expireEndTime);
        // 文件大小
        long maxSize = 10 * 1024 * 1024;
        // 回调
        JSONObject callback = new JSONObject();
        //设置阿里云返回的连接
        callback.put("callbackUrl", uploadParam.getCallbackUrl());
        //设置阿里云返回的body内容
        callback.put("callbackBody", "filename=${object}&size=${size}&etag=${etag}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}&uuid=${x:var1}&uploadUrl=${x:var2}&oFileName=${x:var3}&fnType=${x:var4}");
        //设置阿里云返回的数据类型
        callback.put("callbackBodyType", "application/x-www-form-urlencoded");
        try {
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            String postPolicy = aliyunOssClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String policy = BinaryUtil.toBase64String(binaryData);
            String signature = aliyunOssClient.calculatePostSignature(postPolicy);
            String callbackData = BinaryUtil.toBase64String(callback.toString().getBytes("utf-8"));
            // 返回结果
            //阿里云key
            result.put("OSSAccessKeyId", aliyunOssClient.getCredentialsProvider().getCredentials().getAccessKeyId());
            //签名
            result.put("policy", policy);
            result.put("signature", signature);
            result.put("key", dir);
            result.put("callback", callbackData);
            result.put("success_action_status", "200");
//            result.put("action", action);
            result.put("uploadUrl", uploadParam.getOssUrlPrefix());
            result.put("bucketName", uploadParam.getBucket());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
