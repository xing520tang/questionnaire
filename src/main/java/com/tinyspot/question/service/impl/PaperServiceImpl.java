package com.tinyspot.question.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.tinyspot.question.entity.Paper;
import com.tinyspot.question.entity.PaperListItem;
import com.tinyspot.question.entity.Question;
import com.tinyspot.question.mapper.PaperMapper;
import com.tinyspot.question.mapper.QuestionMapper;
import com.tinyspot.question.service.PaperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author tinyspot
 * @Time 2019/12/2-14:26
 */
@Service
public class PaperServiceImpl implements PaperService {

    private static Logger LOG = LoggerFactory.getLogger(PaperServiceImpl.class);

    @Autowired
    PaperMapper paperMapper;

    @Autowired
    QuestionMapper questionMapper;
    /**
     * 将新的问卷信息和问题存入数据库，创建新的问卷
     * 是一个事务方法，若问卷信息或者问题信息有一处出错，则全部回滚
     * @param paper 问卷信息
     * @param questions 问卷中的问题
     * @return 状态码
     */
    @Override
    @Transactional //默认的传播行为级别Propagation.REQUIRED
    public int createNewPaper(Paper paper, List<Question> questions) {
        int code = 1;
        try {
            //先存问卷信息
            paperMapper.insertPaper(paper);
        } catch (Exception e) {
            LOG.error("PaperService.createNewPaper问卷信息插入失败！", e);
            code = 0;
            throw new RuntimeException("插入问卷信息异常");
        }

        try {
            //得到问卷id
            Integer paperId = paper.getId();
            //每个问题的paperId需要设置
            for(Question question : questions)
                question.setPaperId(paperId);
            //数据库批量插入问题
            questionMapper.insertQuestionsBatch(questions);
        } catch (Exception e) {
            LOG.error("PaperService.createNewPaper问题插入失败！", e);
            code = 0;
            throw new RuntimeException("插入问题异常");
        }

        return code;
    }

    /*
    获取所有的问卷，封装为PaperListItem对象
     */
    @Override
    public List<PaperListItem> getPaperList() {
        List<PaperListItem> list = paperMapper.selectAllPapersAndAuthorNick();
        return list;
    }

    /*
    获取某个用户的所有问卷，基本同上述方法
     */
    @Override
    public List<PaperListItem> getUsersPaperList(Integer userId) {
        if (ObjectUtil.isEmpty(userId)) //未登录
            return null;
        List<PaperListItem> list = paperMapper.selectAllPapersAndAuthorNickByUserId(userId);
        return list;
    }

    /**
     * 获取问卷与其作者的信息
     * @param paperId 传入问卷id
     * @return
     */
    @Override
    public PaperListItem getPaper(Integer paperId) {
        PaperListItem paperListItem = paperMapper.getPaperAndAuthorIdAndNickByPaperId(paperId);
        return paperListItem;
    }
}
