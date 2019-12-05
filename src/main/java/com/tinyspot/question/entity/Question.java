package com.tinyspot.question.entity;

import lombok.Data;

/**
 * @Author tinyspot
 * @Time 2019/12/2-13:41
 */
@Data
public class Question {
    private Integer id;     //问题编号
    private Integer paperId;//该问题所属问卷id
    private Integer no;     //该问题所在问卷的题号
    private Integer type;   //问题类型0单选1多选2简答
    private String description;//题目描述
    private Integer optionsNum;//选项个数
    private String optionsInfo;//选项具体信息
    private String remark;  //问题备注
}
