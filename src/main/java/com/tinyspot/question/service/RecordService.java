package com.tinyspot.question.service;

import com.tinyspot.question.entity.Answers;
import com.tinyspot.question.entity.Records;

import java.util.List;

/**
 * @Author tinyspot
 * @Time 2019/12/5-13:37
 * 与答题记录有关的逻辑
 */
public interface RecordService {

    int createNewRecord(Records records, List<Answers> answers);
}
