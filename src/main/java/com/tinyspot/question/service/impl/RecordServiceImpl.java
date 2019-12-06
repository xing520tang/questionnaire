package com.tinyspot.question.service.impl;

import com.tinyspot.question.entity.Answers;
import com.tinyspot.question.entity.PublishListItem;
import com.tinyspot.question.entity.Question;
import com.tinyspot.question.entity.Records;
import com.tinyspot.question.mapper.RecordMapper;
import com.tinyspot.question.service.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author tinyspot
 * @Time 2019/12/5-13:39
 */
@Service
public class RecordServiceImpl implements RecordService {
    private static Logger LOG = LoggerFactory.getLogger(PaperServiceImpl.class);

    @Autowired
    RecordMapper recordMapper;

    /**
     *
     * @param record
     * @param answers
     * @return 记录的id，如果返回0，表示存储失败
     */
    @Override
    @Transactional
    public int createNewRecord(Records record, List<Answers> answers) {
        int code = 2;
        try {
            //答题问卷信息
            recordMapper.insertRecord(record);
        } catch (Exception e) {
            LOG.error("PaperService.createNewRecord答题信息插入失败！", e);
            code = 0;
            throw new RuntimeException("插入答题信息异常");
        }

        try {
            //得到问卷id
            Integer recordId = record.getId();
            //每个问题的paperId需要设置
            for (Answers answer : answers) {
                answer.setRecordId(recordId);
                answer.setPaperId(record.getPaperId());
            }
            //数据库批量插入问题
            recordMapper.insertAnswersBatch(answers);
        } catch (Exception e) {
            LOG.error("PaperService.createNewRecord题目答案插入失败！", e);
            code = 0;
            throw new RuntimeException("插入答案异常");
        }

        return record.getId();
    }

    @Override
    public List<PublishListItem> getUsersPublishDolist(Integer paperId) {
        List<PublishListItem> answerInfos = null;
        try {
            answerInfos = recordMapper.getRecordsByPaperId(paperId);
        } catch (Exception e) {
            LOG.error("获取问卷作答信息异常", e);
        }
        return answerInfos;
    }
}
