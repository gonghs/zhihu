package com.maple;

import com.maple.entity.ResultBody;
import com.maple.utils.FileUtils;
import com.maple.utils.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TYZ034 on 2018/3/13.
 */

@Controller
@RequestMapping("/ue")
public class UEditorController {
    @RequestMapping("/fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam(required = false) MultipartFile[] uploadfile) {
        Map map = new HashMap();
        String url = FileUtils.saveFile(uploadfile[0]);
        map.put("url","http://bucket-yytour-test.oss-cn-hangzhou.aliyuncs.com/yytour/ueditorUpload/20180312155835_42.wmv");
        map.put("state", "SUCCESS");
        map.put("title", uploadfile[0].getName());
        return JsonUtils.Object2String(map);
    }

    @RequestMapping("/queryConfig")
    @ResponseBody
    public String queryConfig() {
        Map map = new HashMap();
        map.put("xxx","xxxx");
//        map.put("imageUrl","/ue/fileUpload");
//        map.put("scrawlActionUrl","/ue/scrawlUpload");
        return JsonUtils.Object2String(map);
    }

    @RequestMapping("/scrawlUpload")
    @ResponseBody
    public String scrawl(HttpServletRequest request) {
        Map map = new HashMap();
        String base64String = request.getParameter("uploadfile");
        String url = FileUtils.base64String2Image(base64String);
        map.put("url", "http://bucket-yytour-test.oss-cn-hangzhou.aliyuncs.com/yytour/ueditorUpload/20180312155835_42.wmv");
        map.put("state", "SUCCESS");
        return JsonUtils.Object2String(map);
    }


    @RequestMapping("/queryList")
    @ResponseBody
    public ResultBody queryList(HttpServletRequest request) {
        ResultBody rb = new ResultBody();
        List list = FileUtils.getFileList();
        rb.setList(list);
        rb.setStart(0);
        return rb;
    }
}
