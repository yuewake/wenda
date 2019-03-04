package com.yue.wenda.controller;

import com.alibaba.fastjson.JSONObject;
import com.yue.wenda.model.*;
import com.yue.wenda.service.*;
import com.yue.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yue on 2019/2/28
 */
@Controller
@RequestMapping("/question")
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    QuestionService questionService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @Autowired
    FollowService followService;
    /**
     * 提问 必须登陆才能提问
     * @param title 问题标题
     * @param content 问题描述
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title, @RequestParam("content") String content) {
        try {
            Question question = new Question();
            question.setContent(content);
            question.setCreatedDate(new Date());
            question.setTitle(title);
            question.setUserId(hostHolder.getUser().getId());

            if (questionService.addQuestion(question) > 0) {
                return WendaUtil.getJSONString(0);
            }
        } catch (Exception e) {
            logger.error("增加题目失败" + e.getMessage());
        }
        return WendaUtil.getJSONString(1, "失败");
    }

    /**
     * 问题的详细信息
     * @return
     */
    @RequestMapping(value = "/{qid}", method = RequestMethod.GET)
    public String questionDtail(@PathVariable("qid") int id, Model model){
        Question q = questionService.getById(id);
        model.addAttribute("question",q);
        List<ViewObject> vos = new LinkedList<>();
        //获取该用户是否关注该问题
        boolean isfollow = followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, id);
        int isf = isfollow ? 1 : 0;
        System.out.println("===========================是否关注问题" + isf);
        model.addAttribute("isfollow", isf);
        //1获取该问题的所有评论
        List<Comment> comments = commentService.getCommentsByEntity(id, EntityType.ENTITY_QUESTION);
        //2 遍历评论comments 获取每一条评论的用户信息和点赞数 将其存到viewObject对象中
        for (Comment comment: comments) {
            long like = likeService.getLikeCount(EntityType.ENTITY_COMMENT, comment.getId());
            int likeStatus = likeService.getLikeStatus(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT,comment.getId());
            ViewObject vo = new ViewObject();
            User user = userService.getUser(comment.getUserId());
            vo.set("likeStatus", likeStatus);
            vo.set("like", like);
            vo.set("user", user);
            vo.set("comment", comment);
            vos.add(vo);
        }
        model.addAttribute("vos", vos);
        return "detail";
    }

}
