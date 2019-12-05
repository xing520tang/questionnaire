package com.tinyspot.question.service.impl;

import com.tinyspot.question.entity.Answers;
import com.tinyspot.question.mapper.AnswerMapper;
import com.tinyspot.question.service.AnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author tinyspot
 * @Time 2019/12/5-19:46
 */
@Service
public class AnswerServiceImpl implements AnswerService {

    private static Logger LOG = LoggerFactory.getLogger(PaperServiceImpl.class);

    @Autowired
    AnswerMapper answerMapper;

    @Override
    public List<Answers> getAnswers(Integer recordId, Integer paperId) {
        List<Answers> list = null;
        try {
            list = answerMapper.getAnswersByRecordIdAndPaperId(recordId, paperId);
        } catch (Exception e) {
            LOG.error("查询答案出错", e);
        }
        return list;
    }
}
