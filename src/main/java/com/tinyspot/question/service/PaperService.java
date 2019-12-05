package com.tinyspot.question.service;

import com.tinyspot.question.entity.Paper;
import com.tinyspot.question.entity.PaperListItem;
import com.tinyspot.question.entity.Question;

import java.util.List;

/**
 * @Author tinyspot
 * @Time 2019/12/2-14:24
 */
public interface PaperService {

    int createNewPaper(Paper paper, List<Question> questions);

    List<PaperListItem> getPaperList();

    List<PaperListItem> getUsersPaperList(Integer userId);

    PaperListItem getPaper(Integer paperId);
}
