package com.tinyspot.question;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinyspot.question.entity.*;
import com.tinyspot.question.mapper.UserMapper;
import com.tinyspot.question.util.CommonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionApplicationTests {
/*
    @Autowired
    UserMapper userMapper;

    @Test
    public void contextLoads() {
        User user = new User();
    }


    @Test
    public void testSHA256(){
        String src = "123456";
        String code = CommonUtils.genSHA_256(src);
        System.out.println(code);

        System.out.println(DateUtil.date(System.currentTimeMillis()));
    }

    @Test
    public void testSql(){
        User user = userMapper.getUserByUserNameAndPassword("tiny_spot", CommonUtils.genSHA_256("asdfadf"));

        System.out.println(user);
    }

    @Test
    public void testDeleteFile(){
        String path = "D:/shixi/Questionnaire_investigation/static/img/56821ee88a905.jpg";
        File file = new File(path);
        System.out.println(file.delete());
    }

    @Test
    public void testString(){
        String path = "d:/shixi/static";
        Object obj = new Integer(3);
        System.out.println(path + String.valueOf(obj));
        StringBuilder sb = new StringBuilder(path);
        sb.append(obj);
        System.out.println(sb + ".jpg");
    }
*/

//    @Test
//    public void testJson(){
//        String jsonString = "{\"title\":\"问卷标题\",\"subTitle\":\"副标题\",\"type\":\"0\",\"description\":\"问卷描述\",\"startDate\":\"2019-12-02 13:36:11\",\"endDate\":\"2019-12-02 13:36:13\",\"timeLimit\":\"30\",\"remark\":\"备注\"}&[{\"no\":\"1\",\"type\":\"0\",\"description\":\"第一题题目描述\",\"optionsNum\":\"2\",\"optionsInfo\":\"选项1#op%选项2\",\"remark\":\"备注\",\"scores\":\"1\",\"standText\":\"\"},{\"no\":\"2\",\"type\":\"1\",\"description\":\"第二题题目表述\",\"optionsNum\":\"3\",\"optionsInfo\":\"1选项#op%2选项#op%3选项\",\"remark\":\"2题备注\",\"scores\":\"\",\"standText\":\"\"}]" ;
//        String[] strs = jsonString.split("&");
//        Paper paper = JSONUtil.toBean(strs[0], Paper.class);
//        List<Question> list = JSONUtil.toList(JSONUtil.parseArray(strs[1]), Question.class);
//        System.out.println(paper);
//        System.out.println("list: " + list);
//        System.out.println(list.size());
//    }

    @Test
    public void test1()  {
        //String jsonstr = "{\"paperId\":3,\"userId\":null,\"useTime\":0,\"answerDate\":1575516242226}&[{\"questionId\":\"0\",\"answerNo\":\"0\"},{\"questionId\":\"1\",\"answerNo\":\"2\"},{\"questionId\":\"2\",\"answerNo\":\"0;1;2;3\"},{\"questionId\":\"3\",\"answerNo\":\"跑步\\r\\n\"}]";
        String jsonstr = "{\"paperId\":3,\"userId\":null,\"useTime\":0,\"answerDate\":1575518513394}&[{\"questionId\":\"0\",\"answerNo\":\"3\"},{\"questionId\":\"1\",\"answerNo\":\"1\"},{\"questionId\":\"2\",\"answerNo\":\"12\"}]";
        String[] strs = jsonstr.split("&");
        Records records = JSONUtil.toBean(strs[0], Records.class);
        List<Answers> answers = JSONUtil.toList(JSONUtil.parseArray(strs[1]), Answers.class);
        System.out.println(records);
        System.out.println(answers);
//        System.out.println(answers.size());
//        System.out.println(answers.get(0).getPaperId());
    }
}
