package com.tinyspot.question.service;

import com.tinyspot.question.entity.Question;

import java.util.List;

/**
 * @Author tinyspot
 * @Time 2019/12/3-21:15
 * 和问题有关的逻辑
 */
public interface QuestionService {
    List<Question> getQuestions(Integer paperId);
}
