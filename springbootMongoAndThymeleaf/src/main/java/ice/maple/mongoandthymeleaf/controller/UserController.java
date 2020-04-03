package ice.maple.mongoandthymeleaf.controller;

import ice.maple.mongoandthymeleaf.dao.IUserDao;
import ice.maple.mongoandthymeleaf.dao.impl.UserDao;
import ice.maple.mongoandthymeleaf.model.User;
import ice.maple.mongoandthymeleaf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    //http://localhost:8080
    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("users",userService.getUsers());
        return "user";
    }

    //http://localhost:8080
    @RequestMapping("/haha")
    public String haha(Model model){
//        model.addAttribute("users",userService.getUsers());
        return "haha";
    }

    //http://localhost:8080/user/queryUsers
    @RequestMapping(value = "/queryUsers")
    @ResponseBody
    public List<User> queryUsers(){
        return userService.getUsers();
    }
    //http://localhost:8080/user/getUser/xxx
    @RequestMapping("/getUser/{id}")
    @ResponseBody
    public User getUser(@PathVariable String id){
        return userService.getUser(id);
    }

    //http://localhost:8080/user/add?name=xxx&sex=xxx
    @RequestMapping("/add")
    @ResponseBody
    public void insert(User user){
        userService.add(user);
    }

    //http://localhost:8080/user/update?id=xxx&name=xxx&sex=xxx
    @RequestMapping("/update")
    @ResponseBody
    public void update(User user){
        userService.update(user);
    }

    //http://localhost:8080/user/delete/xxx
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public void delete(@PathVariable String id){
        userService.delete(id);
    }

}
