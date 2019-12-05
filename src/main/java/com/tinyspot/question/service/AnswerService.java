package com.tinyspot.question.service;

import com.tinyspot.question.entity.Answers;

import java.util.List;

/**
 * @Author tinyspot
 * @Time 2019/12/5-19:45
 */
public interface AnswerService {
    List<Answers> getAnswers(Integer recordId, Integer paperId);
}
