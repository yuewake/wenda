package com.yue.wenda.service;

import com.yue.wenda.dao.LoginTicketDao;
import com.yue.wenda.dao.UserDao;
import com.yue.wenda.model.LoginTicket;
import com.yue.wenda.model.User;
import com.yue.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    LoginTicketDao loginTicketDao;

    /**
     * 注册
     * @param user
     * @return
     */
    public Map<String, Object> regist(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (user.getUsername() == null || "".equals(user.getUsername().trim())) {
            map.put("msg", "用户名为空");
            return map;
        } else if (user.getPassword() == null || "".equals(user.getPassword().trim())) {
            map.put("msg", "密码为空");
            return map;
        } else if (userDao.selectByName(user) != null) {
            map.put("msg", "用户名已被注册");
            return map;
        } else {
            String salt = UUID.randomUUID().toString().replaceAll("-", "");
            user.setSalt(salt);
            user.setPassword(WendaUtil.MD5(salt));
            userDao.insertUser(user);
            return map;
        }
    }

    /**
     * 登陆
     * @param user
     * @return
     */
    public Map<String, Object> login(User user){
        Map<String, Object> map = new HashMap<String, Object>();
        //1 检查用户名和密码是否为空
        //2 检查用户名是否存在以及密码是否正确

        //3 所有都准确无误的情况下 创建一个ticket给这个用户
        user = userDao.selectByName(user);
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    /**
     * 添加ticket
     * @param userId
     * @return
     */
    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);//设置过期时间为24小时
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDao.addTicket(ticket);
        return ticket.getTicket();
    }

    public void logout(String ticket){
        loginTicketDao.updateStatus(ticket, 1);
    }

}
