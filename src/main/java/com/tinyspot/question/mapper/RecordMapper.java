package com.tinyspot.question.mapper;

import com.tinyspot.question.entity.Answers;
import com.tinyspot.question.entity.Records;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author tinyspot
 * @Time 2019/12/5-13:47
 */
@Mapper
public interface RecordMapper {

    /*
    插入答题记录信息
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into records(paper_id, user_id, answer_date, use_time) " +
            "values(#{paperId},#{userId},#{answerDate},#{useTime})")
    void insertRecord(Records record);


    /*
    批量插入答案
     */
    @Insert("<script>"  +
            "INSERT INTO answers(record_id,paper_id,question_id,answer_no,answer_text) VALUES" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\">" +
            "(#{item.recordId},#{item.paperId},#{item.questionId}, #{item.answerNo}, #{item.answerText})" +
            "</foreach>" +
            "</script>")
    void insertAnswersBatch(@Param("list") List<Answers> answers);
}
