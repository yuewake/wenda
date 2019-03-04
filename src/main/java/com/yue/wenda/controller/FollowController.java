package com.yue.wenda.controller;

import com.yue.wenda.async.EventProducer;
import com.yue.wenda.model.EntityType;
import com.yue.wenda.model.HostHolder;
import com.yue.wenda.service.CommentService;
import com.yue.wenda.service.FollowService;
import com.yue.wenda.service.QuestionService;
import com.yue.wenda.service.UserService;
import com.yue.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

/**
 * Created by yue on 2019/3/2
 */
@Controller
public class FollowController {

    private static final Logger logger = LoggerFactory.getLogger(FollowController.class);

    @Autowired
    FollowService followService;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping("/followQuestion")
    @ResponseBody
    public String followQuestion(String questionId){
        try {
            boolean isSuccess = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, Integer.parseInt(questionId));
            if(isSuccess){
                System.out.println("成功了");
                return WendaUtil.getJSONString(1);
                //TODO 关注成功后 需要通知问题的发布者 使用异步队列
            }else{
                System.out.println("没有成功");
                return WendaUtil.getJSONString(0);
            }
        }catch (Exception e){
            logger.error("出现异常："+e.getMessage());
            return WendaUtil.getJSONString(0);
        }

    }

    @RequestMapping("/unfollowQuestion")
    @ResponseBody
    public String unfollowQuestion(String questionId){
        try{
            boolean isSuccess = followService.unfollow(hostHolder.getUser().getId(),EntityType.ENTITY_QUESTION, Integer.parseInt(questionId));
            if(isSuccess){
                System.out.println("成功了");
                return WendaUtil.getJSONString(1);
                //TODO 关注成功后 需要通知问题的发布者 使用异步队列
            }else{
                System.out.println("没有成功");
                return WendaUtil.getJSONString(0);
            }
        }catch (Exception e){
            logger.error("出现异常：" + e.getMessage());
            return WendaUtil.getJSONString(0);
        }
    }
}
