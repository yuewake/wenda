package com.yue.wenda.async.handler;

import com.yue.wenda.async.EventHandler;
import com.yue.wenda.async.EventModel;
import com.yue.wenda.async.EventType;
import com.yue.wenda.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yue on 2019/3/2
 */
@Component
public class LikeHandler implements EventHandler {

    @Autowired
    LikeService likeService;

    @Override
    public void doHandle(EventModel model) {
        if("LIKE" == model.getEventType().toString()) {
            likeService.like(model.getUserId(), model.getEntityType(), model.getEntityId());
        }else {
            likeService.disLike(model.getUserId(), model.getEntityType(), model.getEntityId());
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        List<EventType> le = new LinkedList<EventType>();
        le.add(EventType.LIKE);
        le.add(EventType.DISLIKE);
        return le;
    }
}
