package com.yue.wenda.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.yue.wenda.async.EventHandler;
import com.yue.wenda.async.EventModel;
import com.yue.wenda.async.EventType;
import com.yue.wenda.model.EntityType;
import com.yue.wenda.model.Feed;
import com.yue.wenda.model.Question;
import com.yue.wenda.model.User;
import com.yue.wenda.service.FeedService;
import com.yue.wenda.service.FollowService;
import com.yue.wenda.service.QuestionService;
import com.yue.wenda.service.UserService;
import com.yue.wenda.util.JedisAdapter;
import com.yue.wenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by yue on 2019/3/4
 */
public class FeedHandler implements EventHandler {
    @Autowired
    FollowService followService;

    @Autowired
    UserService userService;

    @Autowired
    FeedService feedService;

    @Autowired
    JedisAdapter jedisAdapter;

    @Autowired
    QuestionService questionService;


    private String buildFeedData(EventModel model) {
        Map<String, String> map = new HashMap<String ,String>();
        // 触发用户是通用的
        User actor = userService.getUser(model.getUserId());
        if (actor == null) {
            return null;
        }
        map.put("userId", String.valueOf(actor.getId()));
        map.put("userHead", actor.getHeadUrl());
        map.put("userName", actor.getUsername());

        if (model.getEventType() == EventType.COMMENT ||
                (model.getEventType() == EventType.FOLLOW  && model.getEntityType() == EntityType.ENTITY_QUESTION)) {
            Question question = questionService.getById(model.getEntityId());
            if (question == null) {
                return null;
            }
            map.put("questionId", String.valueOf(question.getId()));
            map.put("questionTitle", question.getTitle());
            return JSONObject.toJSONString(map);
        }
        return null;
    }

    @Override
    public void doHandle(EventModel model) {
        // 为了测试，把model的userId随机一下
        Random r = new Random();
        model.setUserId(1+r.nextInt(10));

        // 构造一个新鲜事
        Feed feed = new Feed();
        feed.setCreatedDate(new Date());
        feed.setType(model.getEventType().getValue());
        feed.setUserId(model.getUserId());
        feed.setData(buildFeedData(model));
        if (feed.getData() == null) {
            // 不支持的feed
            return;
        }
        feedService.addFeed(feed);

        // 获得所有粉丝
        List<Integer> followers = followService.getFollowers(EntityType.ENTITY_USER, model.getUserId(), Integer.MAX_VALUE);
        // 系统队列
        followers.add(0);
        // 给所有粉丝推事件
        for (int follower : followers) {
            String timelineKey = RedisKeyUtil.getTimelineKey(follower);
            jedisAdapter.lpush(timelineKey, String.valueOf(feed.getId()));
            // 限制最长长度，如果timelineKey的长度过大，就删除后面的新鲜事
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(new EventType[]{EventType.COMMENT, EventType.FOLLOW});
    }
}
