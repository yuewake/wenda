package com.yue.wenda.async;

import java.util.List;

/**
 * Created by yue on 2019/3/2
 */
public interface EventHandler {

    void doHandle(EventModel model);
    List<EventType> getSupportEventTypes();//存有它支持处理的事件类型
}
