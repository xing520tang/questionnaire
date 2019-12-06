package com.tinyspot.question.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author tinyspot
 * @Time 2019/12/6-16:07
 */
@Data
public class PaperDidListItem {
    private Integer paperId;    //问卷id
    private Integer recordId;   //记录id
    private Integer authorId;   //作者id
    private String nickName;    //问卷作者昵称
    private Integer type;       //问卷类型
    private String title;       //问卷主标题
    private String subTitle;    //问卷副标题
    private String description; //问卷描述
    private Date addDate;       //问卷创建日期
    private Date startDate;     //问卷开始日期
    private Date endDate;       //问卷回收日期
    private String imgName;     //头像名称
    private Long timeLimit;     //时间限制
}
