package com.yue.wenda.controller;

import com.yue.wenda.async.EventModel;
import com.yue.wenda.async.EventProducer;
import com.yue.wenda.async.EventType;
import com.yue.wenda.model.Comment;
import com.yue.wenda.model.EntityType;
import com.yue.wenda.model.HostHolder;
import com.yue.wenda.service.CommentService;
import com.yue.wenda.service.LikeService;
import com.yue.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yue on 2019/3/1
 */
@Controller
public class LikeController {
    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        Comment comment = commentService.getCommentById(commentId);
        EventModel em = new EventModel();
        em.setEventType(EventType.LIKE);
        em.setEntityId(commentId);
        em.setEntityOwnerId(comment.getUserId());
        em.setEntityType(EntityType.ENTITY_COMMENT);
        em.setUserId(hostHolder.getUser().getId());
        eventProducer.fireEvent(em);
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId) {

        Comment comment = commentService.getCommentById(commentId);
        EventModel em = new EventModel();
        em.setEventType(EventType.DISLIKE);
        em.setEntityId(commentId);
        em.setEntityOwnerId(comment.getUserId());
        em.setEntityType(EntityType.ENTITY_COMMENT);
        em.setUserId(hostHolder.getUser().getId());
        eventProducer.fireEvent(em);
        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJSONString(0, String.valueOf(likeCount));
    }
}
