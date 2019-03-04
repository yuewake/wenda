package com.yue.wenda.async;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yue on 2019/3/2
 */
public class EventModel {

    private EventType eventType;//触发的事件类型
    private int userId; //触发该事件的人
    private int entityType;
    private int entityId;
    private int entityOwnerId;

    private Map<String, String> exts = new HashMap<>();//将需要触发的handler放到此处 目前没用到
    public EventModel setExt(String key, String value) {
        exts.put(key, value);
        return this;
    }
    public String getExt(String key) {
        return exts.get(key);
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public void setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
    }


}
