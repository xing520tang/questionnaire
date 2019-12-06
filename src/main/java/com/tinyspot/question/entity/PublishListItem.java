package com.tinyspot.question.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author tinyspot
 * @Time 2019/12/5-21:13
 */
@Data
public class PublishListItem {
    private Integer id;         //答题记录编号
    private Integer authorId;   //答题者id
    private String nickName;    //答题者昵称
    private String imgName;     //答题者头像名称
    private Integer scores;     //问卷得分

    private Integer type;       //问卷类型
    private String title;       //问卷主标题
    private String subTitle;    //问卷副标题
    private String description; //问卷描述
    private Date addDate;       //问卷创建日期
    private Date startDate;     //问卷开始日期
    private Date endDate;       //问卷回收日期
    private Long timeLimit;     //时间限制
}
