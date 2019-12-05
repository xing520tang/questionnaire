package com.tinyspot.question.mapper;

import com.tinyspot.question.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author tinyspot
 * @Time 2019/12/2-20:50
 * 和问题有关的数据库语句
 */
@Mapper
public interface QuestionMapper {

    /*
    批量插入问题
     */
    @Insert("<script>"  +
            "INSERT INTO questions(paper_id,no,type,description,options_num,options_info,remark) VALUES" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\">" +
            "(#{item.paperId},#{item.no},#{item.type}, #{item.description}, #{item.optionsNum}," +
            "#{item.optionsInfo}, #{item.remark})" +
            "</foreach>" +
            "</script>")
    void insertQuestionsBatch(@Param("list") List<Question> questions);

    /*
    根据问卷id查询该问卷的所有问题
     */
    @Select("SELECT * FROM questions WHERE paper_id=#{paperId}")
    List<Question> getQuestionsByPaperId(@Param("paperId") Integer paperId);
}
