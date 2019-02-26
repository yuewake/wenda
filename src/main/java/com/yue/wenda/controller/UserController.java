package com.yue.wenda.controller;

import com.yue.wenda.model.User;
import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

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
     * 进行登陆
     * @return
     */
    @RequestMapping("login")
    public String login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username + ":" + password);
        return "index";
    }
}
