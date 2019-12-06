package com.tinyspot.question.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.tinyspot.question.entity.*;
import com.tinyspot.question.service.AnswerService;
import com.tinyspot.question.service.PaperService;
import com.tinyspot.question.service.QuestionService;
import com.tinyspot.question.service.RecordService;
import com.tinyspot.question.util.ResultGeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author tinyspot
 * @Time 2019/11/26-17:19
 * 和问卷相关的请求
 */
@RestController
@RequestMapping("/paper")
public class PaperController {

    private static Logger LOG = LoggerFactory.getLogger(PaperController.class);

    @Autowired
    PaperService paperService;

    @Autowired
    QuestionService questionService;

    @Autowired
    RecordService recordService;

    @Autowired
    AnswerService answerService;

    /**
     * 发布问卷接口，paper_new.html
     * @param jsonString 问卷信息与试题信息
     * @param session 为了获取userId
     * @return
     */
    @PostMapping("")
    public Result newPaper(@RequestBody String jsonString, HttpSession session){
        LOG.debug(jsonString);
        //将问卷信息与问题分开
        String[] strs = jsonString.split("&");
        //得到问卷对象
        Paper paper = JSONUtil.toBean(strs[0], Paper.class);
        //得到所有的问题对象
        List<Question> questions = JSONUtil.toList(JSONUtil.parseArray(strs[1]), Question.class);
        LOG.debug(paper.toString());
        LOG.debug(questions.toString());

        //先将问卷作者id赋值
        paper.setAuthorId((Integer) session.getAttribute("userId")); //设置作者id
        //设置问卷创建日期
        paper.setAddDate(new Date());
        //创建新问卷
        int code = paperService.createNewPaper(paper, questions);

        Result res = ResultGeneratorUtil.genFailResult();
        //问卷创建成功
        if (code == 1) {
            res.setCode(ResultGeneratorUtil.RESULT_CODE_SUCCESS);
            res.setMsg(ResultGeneratorUtil.DEFAULT_SUCCESS_MESSAGE);
        }
        return res;
    }


    /**
     * 获取所有的问卷，且是分页查询  paper_list.html
     * @param pageSize 每一页大小
     * @param offset   偏移
     * @return
     */
    @GetMapping("/list")
    public Map<String, Object> getAllPaper(@RequestParam(value = "limit", defaultValue = "10") Integer pageSize
            , @RequestParam(value = "offset", defaultValue = "0") Integer offset){
        LOG.debug("/paper/list...GET");
        int pageNum = offset/pageSize + 1; //第几页
        LOG.debug("第" + pageNum + "页, 每页"+pageSize);

        //开启分页，从pn也开始，一页10个数据
        PageHelper.startPage(pageNum, pageSize);
        //pagehelper后面紧跟的就是一个分页查询
        List<PaperListItem> allPapers = paperService.getPaperList();
        LOG.debug(allPapers.toString());

        //生成结果集
        Map<String, Object> res = new HashMap<>();
        res.put("rows", allPapers);
        res.put("total", allPapers.size());

        return res;
    }


    /**
     * 前提是已经登录
     * 获取该登录用户的所有发布的问卷，分页查询，member_publish.html
     * @param pageSize
     * @param offset
     * @param session
     * @return
     */
    @GetMapping("/user/list")
    public Map<String, Object> getUsersPaper(@RequestParam(value = "limit", defaultValue = "10") Integer pageSize
            , @RequestParam(value = "offset", defaultValue = "0") Integer offset
            , HttpSession session){
        LOG.debug("/paper/user/list...GET");
        int pageNum = offset/pageSize + 1; //第几页
        LOG.debug("第" + pageNum + "页, 每页"+pageSize);

        //开启分页，从pn也开始，一页10个数据
        PageHelper.startPage(pageNum, pageSize);
        //pagehelper后面紧跟的就是一个分页查询
        List<PaperListItem> allPapers = paperService.getUsersPaperList((Integer) session.getAttribute("userId"));
        //LOG.debug(allPapers.toString());

        //生成结果集
        Map<String, Object> res = new HashMap<>();
        res.put("rows", allPapers);
        res.put("total", allPapers.size());

        return res;
    }

    /**
     * 获取用户发布的问卷的答卷信息
     * @param pageSize
     * @param offset
     * @param paperId 问卷id唯一映射到一个用户，所以不需要用户id
     * @return
     */
    @GetMapping("/user/publishDolist/{paperId}")
    public Map<String, Object> getUsersPublishDolist(@RequestParam(value = "limit", defaultValue = "10") Integer pageSize
            , @RequestParam(value = "offset", defaultValue = "0") Integer offset
            , @PathVariable("paperId") Integer paperId){
        LOG.debug("/paper/user/publishDolist/paperid...GET");
        int pageNum = offset/pageSize + 1; //第几页
        LOG.debug("第" + pageNum + "页, 每页"+pageSize);

        //先查询问卷信息
        PaperListItem paperInfo = paperService.getPaper(paperId);
        //开启分页，从pn也开始，一页10个数据
        PageHelper.startPage(pageNum, pageSize);
        //pagehelper后面紧跟的就是一个分页查询
        List<PublishListItem> publishListItems = recordService.getUsersPublishDolist(paperId);
        //List<PaperListItem> allPapers = paperService.getUsersPaperList((Integer) session.getAttribute("userId"));
        //LOG.debug(allPapers.toString());

        //将问卷信息赋值给每个答题信息
        for (PublishListItem item : publishListItems) {
            item.setType(paperInfo.getType());
            item.setTitle(paperInfo.getTitle());
            item.setSubTitle(paperInfo.getSubTitle());
            item.setDescription(paperInfo.getDescription());
            item.setAddDate(paperInfo.getAddDate());
            item.setStartDate(paperInfo.getStartDate());
            item.setEndDate(paperInfo.getEndDate());
            item.setTimeLimit(paperInfo.getTimeLimit());
        }

        //生成结果集
        Map<String, Object> res = new HashMap<>();
        res.put("rows", publishListItems);
        res.put("total", publishListItems.size());

        return res;
    }


    /**
     * 返回问卷信息与问卷中的题目 paper_do.html
     * @param paperId 问卷id
     * @return
     */
    @GetMapping("/do/{paperId}")
    public Map<String, Object> getPaperAndQuestions(@PathVariable(value = "paperId") Integer paperId){
        LOG.debug("/do/paperId...GET");
        LOG.debug(paperId.toString());
        //获取问卷信息
        PaperListItem paper = paperService.getPaper(paperId);
        //获取该问卷中所有的问题
        List<Question> questions = questionService.getQuestions(paperId);
        LOG.debug(paper.toString());
        LOG.debug(questions.toString());
        //生成结果集
        Map<String, Object> res = new HashMap<>();
        res.put("paperInfo", paper);
        res.put("questions", questions);
        return res;
    }


    /**
     * 答题接口 paper_do.html
     * @param jsonStr 答题信息及答案信息
     * @param session 为了获取userId
     * @return
     */
    @PostMapping("/do")
    public Result anwserQuestions(@RequestBody String jsonStr, HttpSession session){
        LOG.debug("/paper/do...POST");
        LOG.debug(jsonStr);

        //将答卷信息与具体答案分开
        String[] strs = jsonStr.split("&");
        //得到问卷对象
        Records records = JSONUtil.toBean(strs[0], Records.class);
        //得到所有的问题对象
        List<Answers> answers = JSONUtil.toList(JSONUtil.parseArray(strs[1]), Answers.class);
        LOG.debug(records.toString());
        LOG.debug(answers.toString());

        //保存答题者的id，如果匿名的话userId为0
        Integer userId = (Integer) session.getAttribute("userId");
        if (ObjectUtil.isEmpty(userId))
            records.setUserId(0);
        else records.setUserId(userId);

        //保存记录与答案
        int code = recordService.createNewRecord(records, answers);

        Result res = ResultGeneratorUtil.genFailResult();
        //问卷创建成功
        if (code != 0) {
            res.setCode(ResultGeneratorUtil.RESULT_CODE_SUCCESS);
            res.setMsg(ResultGeneratorUtil.DEFAULT_SUCCESS_MESSAGE);
            res.setData(code);  //记录id传回前端
        }
        return res;
    }


    /**
     * 根据问卷id和记录id查看问卷答题信息
     * @param paperId
     * @param recordId
     * @return
     */
    @GetMapping("/watch/{paperId}/{recordId}")
    public Map<String, Object> getRecordDetail(@PathVariable(value = "paperId") Integer paperId
                                                , @PathVariable(value="recordId") Integer recordId){
        LOG.debug("/watch/paperId...GET");
        LOG.debug(paperId.toString());
        LOG.debug(recordId.toString());
        //获取问卷信息
        PaperListItem paper = paperService.getPaper(paperId);
        //获取该问卷中所有的问题
        List<Question> questions = questionService.getQuestions(paperId);
        //获取问题对应的答案信息
        List<Answers> answers = answerService.getAnswers(recordId, paperId);
        if(! ObjectUtil.isEmpty(paper))
            LOG.debug(paper.toString());
        if(! ObjectUtil.isEmpty(questions))
            LOG.debug(questions.toString());
        if(! ObjectUtil.isEmpty(answers))
            LOG.debug(answers.toString());
        //生成结果集
        Map<String, Object> res = new HashMap<>();
        res.put("paperInfo", paper);
        res.put("questions", questions);
        res.put("answers", answers);
        return res;
    }


    @GetMapping("/user/doList")
    public Map<String, Object> getUsersDidPaper(@RequestParam(value = "limit", defaultValue = "10") Integer pageSize
            , @RequestParam(value = "offset", defaultValue = "0") Integer offset
            , HttpSession session){
        LOG.debug("/paper/user/doList...GET");
        int pageNum = offset/pageSize + 1; //第几页
        LOG.debug("第" + pageNum + "页, 每页"+pageSize);

        //开启分页，从pn也开始，一页10个数据
        PageHelper.startPage(pageNum, pageSize);
        //pagehelper后面紧跟的就是一个分页查询
        List<PaperDidListItem> userDidPapers = paperService.getUsersDidPaperList((Integer) session.getAttribute("userId"));
        //LOG.debug(allPapers.toString());

        //生成结果集
        Map<String, Object> res = new HashMap<>();
        res.put("rows", userDidPapers);
        res.put("total", userDidPapers.size());

        return res;
    }
}
