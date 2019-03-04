package com.yue.wenda.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BasicErrorController implements ErrorController {

    @Override
    @RequestMapping(value = "error")
    public String getErrorPath() {
        return "error";
    }


}
