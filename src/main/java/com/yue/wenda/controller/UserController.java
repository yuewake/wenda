package com.yue.wenda.controller;

import com.yue.wenda.model.User;
import com.yue.wenda.service.UserService;
import com.yue.wenda.util.WendaUtil;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 跳转到注册页面
     * @return
     */
    @RequestMapping("/toRegist")
    public String toRegist(){
        return "regist";
    }

    /**
     * 跳转到登陆页面
     */
    @RequestMapping("/toLogin")
    public  String toLogin(){
        return "login";
    }

    /**
     * 登陆
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username + ":" + password);
        return "index";
    }

    /**
     * 注册
     * @return
     */
    @RequestMapping("/regist")
   public String regist(User user){
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        user.setSalt(salt);
        user.setPassword(WendaUtil.MD5(salt));
        userService.insertUser(user);
        return "index";
   }
}
