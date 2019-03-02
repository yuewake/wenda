package com.yue.wenda.async;

/**
 * Created by yue on 2019/3/2
 * 事件类型
 */
public enum EventType {
    LIKE(0),COMMENT(1),LOGIN(2),MAIL(3),DISLIKE(4);

    private int value;
    EventType(int value){
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }
}
