package com.tinyspot.question.service.impl;

import com.tinyspot.question.entity.Question;
import com.tinyspot.question.mapper.QuestionMapper;
import com.tinyspot.question.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author tinyspot
 * @Time 2019/12/3-21:23
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private static Logger LOG = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Autowired
    QuestionMapper questionMapper;

    @Override
    public List<Question> getQuestions(Integer paperId) {
        List<Question> questions = questionMapper.getQuestionsByPaperId(paperId);
        return questions;
    }
}
