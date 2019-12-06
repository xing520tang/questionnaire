package com.tinyspot.question.mapper;

import com.tinyspot.question.entity.Paper;
import com.tinyspot.question.entity.PaperDidListItem;
import com.tinyspot.question.entity.PaperListItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author tinyspot
 * @Time 2019/12/2-14:31
 * 和问卷有关的数据库操作
 */
@Mapper
public interface PaperMapper {

    /*
    插入问卷
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into papers(author_id, type, title, sub_title, description, add_date, start_date, end_date, time_limit, remark)" +
            "values(#{authorId},#{type},#{title},#{subTitle},#{description},#{addDate},#{startDate},#{endDate},#{timeLimit},#{remark})")
    int insertPaper(Paper paper);

    /*
    private Integer id;         //问卷id
    private String nickName;     //问卷作者昵称
    private Integer type;       //问卷类型
    private String title;       //问卷主标题
    private String subTitle;    //问卷副标题
    private Date addDate;       //问卷创建日期
    private Date startDate;     //问卷开始日期
    private Date endDate;       //问卷回收日期
    private Long timeLimit;     //时间限制
     */

    /*
    查询所有的问卷，生成列表，列表元素封装成PaperListItem
     */
    @Select("select p.id, p.description, u.id authorId, u.img_name imgName, nick_name, type, title, sub_title, add_date, start_date, end_date, time_limit " +
            " FROM papers p JOIN users u ON p.author_id=u.id")
    List<PaperListItem> selectAllPapersAndAuthorNick();

    /*
    查询用户id为userId的用户的所有问卷，所发布的所有问卷，基本同上一个方法
     */
    @Select("select p.id, p.description, u.id authorId, nick_name, type, title, sub_title, add_date, start_date, end_date, time_limit " +
            " FROM papers p JOIN users u ON p.author_id=u.id where u.id=#{userId}")
    List<PaperListItem> selectAllPapersAndAuthorNickByUserId(@Param("userId") Integer userId);

    /*
    根据问卷id查询问卷信息与作者信息
     */
    @Select("select p.id, p.description, u.id authorId, nick_name, type, title, sub_title, add_date, start_date, end_date, time_limit " +
            " FROM papers p JOIN users u ON p.author_id=u.id where p.id=#{paperId}")
    PaperListItem getPaperAndAuthorIdAndNickByPaperId(@Param("paperId") Integer paperId);


    /*
    根据用户id查询用户的参与情况
     */
    @Select("SELECT p.id paperId, r.id recordId, u.id authorId, nick_name, TYPE, title, sub_title, add_date, start_date, end_date, u.`img_name` imgName, time_limit " +
            "FROM records r LEFT JOIN users u ON r.`user_id`= u.`id` " +
            "LEFT JOIN papers p ON r.`paper_id` = p.`id` " +
            "WHERE u.id = #{userId}")
    List<PaperDidListItem> selectAllUserDidPapers(@Param("userId") Integer userId);
}
