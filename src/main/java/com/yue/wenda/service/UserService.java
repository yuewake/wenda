package com.yue.wenda.service;

import com.yue.wenda.dao.UserDao;
import com.yue.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public void insertUser(User user){

        userDao.insertUser(user);

    }

}
