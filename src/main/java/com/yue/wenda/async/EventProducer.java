package com.yue.wenda.async;

import com.alibaba.fastjson.JSONObject;
import com.yue.wenda.util.JedisAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by yue on 2019/3/2
 */
@Component
public class EventProducer {

    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel){
        try {
            jedisAdapter.lpush("EventQueue", JSONObject.toJSONString(eventModel));
            return true;
        }catch (Exception e){
            logger.error("出现错误：" + e.getMessage());
            return false;
        }
    }


}
