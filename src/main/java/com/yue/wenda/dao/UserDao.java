package com.yue.wenda.dao;

import com.yue.wenda.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserDao {

    void insertUser(User user);//通过xml实现了

    @Select("select * from user where username = #{username}")
    User selectByName(User user);

    @Select("select * from user where id = #{id}")
    User selectById(int id);

}
