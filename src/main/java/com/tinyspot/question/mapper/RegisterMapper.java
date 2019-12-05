package com.tinyspot.question.mapper;

import com.tinyspot.question.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author tinyspot
 * @Time 2019/11/23-16:56
 */
@Mapper
public interface RegisterMapper {

    /*
    根据用户名查询存在几个用户，就是查是否存在有该用户名的用户
     */
    @Select("select count(*) from users where user_name=#{userName}")
    int getCountByUserName(String userName);

    /*
    插入新用户
     */
    @Insert("insert into users(user_name, nick_name, password, register_time) values(#{userName}, #{nickName}, #{password}, #{registerTime})")
    void addUser(User user);
}
