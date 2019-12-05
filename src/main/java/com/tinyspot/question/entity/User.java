package com.tinyspot.question.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author tinyspot
 * @Time 2019/11/23-16:14
 */
@Data
public class User {
    private Integer id;
    private String userName;
    private String nickName;
    private Integer userType;
    private String password;
    private String imgName;
    private Date registerTime;
    private Date lastLoginTime;
}
