package com.tinyspot.question.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author tinyspot
 * @Time 2019/11/26-17:27
 * 问卷实体类
 */
@Data
public class Paper {
    private Integer id;         //问卷id
    private Integer authorId;   //问卷作者的用户id
    private Integer type;       //问卷类型
    private String title;       //问卷主标题
    private String subTitle;    //问卷副标题
    private String description; //问卷描述
    private Date addDate;       //问卷创建日期
    private Date startDate;     //问卷开始日期
    private Date endDate;       //问卷回收日期
    private Long timeLimit;     //时间限制
    private String remark;      //备注
    private String imgName;     //图片名称
}
