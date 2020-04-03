package ice.maple.mongoandthymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
    @RequestMapping(value = "/error/{code}")
    public String error(@PathVariable int code, Model model) {
        String pager = "404";
        switch (code) {
            case 404:
                model.addAttribute("code", 404);
                pager = "404";
                break;
            case 500:
                model.addAttribute("code", 500);
                pager = "500";
                break;
        }
        return pager;
    }
}
