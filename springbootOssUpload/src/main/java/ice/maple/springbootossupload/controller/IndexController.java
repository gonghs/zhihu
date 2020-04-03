package ice.maple.springbootossupload.controller;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import ice.maple.springbootossupload.model.Attach;
import ice.maple.springbootossupload.service.IAttachService;
import ice.maple.springbootossupload.util.UploadParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    IAttachService attachService;

    @Autowired
    UploadParam uploadParam;

    @RequestMapping("index")
    public String index(){
        return "vueUploader";
    }

    @PostMapping("/uploadFile")
    @ResponseBody
    public JSONObject uploadFile(MultipartFile[] file,HttpServletRequest request){
        System.out.println(file[0].getSize());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","成功");
        return jsonObject;
    }

    @GetMapping("/getAttachs")
    @ResponseBody
    public JSONObject getAttachs(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("attachs",attachService.getAttachs());
        return jsonObject;
    }

    @GetMapping("insert")
    @ResponseBody
    public String getAttachs(Attach attach){
        attachService.insert(attach);
        return "success";
    }

    @GetMapping("showImg")
    public String showImg(Model model){
        model.addAttribute("ossUrl",uploadParam.getOssUrlPrefix());
        model.addAttribute("attachs",attachService.getAttachs());
        return "showImg";
    }
}
