package com.yue.wenda.controller;

import com.yue.wenda.model.Question;
import com.yue.wenda.model.ViewObject;
import com.yue.wenda.service.QuestionService;
import com.yue.wenda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    /**
     * 根据userId 获取某userId提出的问题（目前获取方式是 问题按时间排序 取前几）
     * 去过userId为0 那么获取所有问题
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    private List<ViewObject> getQuestions(int userId, int offset, int limit) {
        List<Question> questionList = questionService.getLatestQuestions(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) {

            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping("/")
    public String index(Model model){
        //TODO 要不要分成两个部分 一部分显示所有问题， 一部分显示自己提出的问题
        model.addAttribute("vos", getQuestions(0, 0, 10));
        return "index";
    }




}
