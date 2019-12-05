package com.tinyspot.question.entity;

import lombok.Data;

/**
 * @Author tinyspot
 * @Time 2019/12/5-10:41
 * 答案表
 */
@Data
public class Answers {
    private Integer id;         //答案id
    private Integer recordId;   //答案对应的记录id
    private Integer paperId;    //答案对应的问卷id
    private Integer questionId; //答案对应的问题id
    private String answerNo;   //单选或多选答案编号
    private String answerText;  //简答题答案
    private Integer scores;     //分数
}
