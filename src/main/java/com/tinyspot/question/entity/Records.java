package com.tinyspot.question.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author tinyspot
 * @Time 2019/12/5-10:25
 * 记录表
 */
@Data
public class Records {
    private Integer id;         //记录的id
    private Integer paperId;    //该记录所属问卷的id
    private Integer userId;     //该记录用户的id
    private Date answerDate;    //答题日期
    private Integer useTime;    //答题所用时间
    private Integer scores;     //答题分数
}
