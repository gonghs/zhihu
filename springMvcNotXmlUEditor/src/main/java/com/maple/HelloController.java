package com.maple;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by TYZ034 on 2018/3/12.
 */
@Controller
@RequestMapping("/HC")
public class HelloController {
    @RequestMapping("/index")
    public String hello(HttpServletRequest request,HttpServletResponse response) {
        request.setAttribute("mystring","hello springMVC!");
        return "index";
    }
}
