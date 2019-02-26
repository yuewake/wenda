package com.yue.wenda.dao;

import com.yue.wenda.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserDao {

    void insertUser(User user);

}
