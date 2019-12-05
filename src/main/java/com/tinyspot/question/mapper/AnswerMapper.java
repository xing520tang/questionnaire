package com.tinyspot.question.mapper;

import com.tinyspot.question.entity.Answers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author tinyspot
 * @Time 2019/12/5-19:49
 */
@Mapper
public interface AnswerMapper {

    /*
    根据记录id和问卷id查询答案们
     */
    @Select("select * from answers where record_id=#{recordId} and paper_id=#{paperId}")
    List<Answers> getAnswersByRecordIdAndPaperId(@Param("recordId") Integer recordId, @Param("paperId") Integer paperId);
}
