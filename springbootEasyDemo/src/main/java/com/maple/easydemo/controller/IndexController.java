package com.maple.easydemo.controller;

import com.maple.easydemo.YamlPropertySourceFactory;
import com.maple.easydemo.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @Autowired
    private User user;

    @ResponseBody
    @RequestMapping(value = "/",produces = "application/xml;charset=UTF-8")
    User index(){
        return user;
    }

}
