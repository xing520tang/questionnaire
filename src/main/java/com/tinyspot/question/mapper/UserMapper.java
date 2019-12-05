package com.tinyspot.question.mapper;

import com.tinyspot.question.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Author tinyspot
 * @Time 2019/11/25-11:37
 */
@Mapper
public interface UserMapper {

    /*
        根据用户名和密码查询用户
     */
    @Select("select * from users where user_name=#{userName} and password=#{password}")
    User getUserByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);


    /*
        根据用户id查询用户
     */
    @Select("select * from users where id=#{userId}")
    User getUserByUserId(@Param("userId") Integer userId);

    /*
    根据用户id更新用户昵称
     */
    @Update("update users set nick_name=#{nickName} where id = #{userId}")
    void updateNickName(@Param("nickName") String nickName, @Param("userId") Integer userId);

    /*
    根据用户id和密码查询用户数量
     */
    @Select("select count(*) from users where id=#{userId} and password=#{prePass}")
    int getUserByUserIdAndPassword(@Param("userId") Integer userId, @Param("prePass") String prePass);

    /*
    根据用户Id更新用户密码
     */
    @Update("update users set password=#{password} where id = #{userId}")
    void updatePassword(@Param("userId") Integer userId, @Param("password") String s);

    @Update("update users set img_name=#{imgName} where id = #{userId}")
    void updateImgName(@Param("imgName") String s, @Param("userId") Integer userId);
}
