package com.tinyspot.question.util;

import com.tinyspot.question.entity.Result;

/**
 * @Author tinyspot
 * @Time 2019/11/15-17:06
 * 结果生成器
 */
public class ResultGeneratorUtil {
    public static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    public static final String DEFAULT_FAIL_MESSAGE = "FAIL";
    public static final int RESULT_CODE_SUCCESS = 200;
    public static final int RESULT_CODE_SERVER_ERROR = 500;
    public static final int ALREADY_LOGGED = 210;
    public static final int NEED_LOGIN = 220;

    public static Result genSuccessResult(){
        Result result = new Result(RESULT_CODE_SUCCESS, DEFAULT_SUCCESS_MESSAGE);
        return result;
    }

    public static Result genSuccessResult(String msg){
        Result result = new Result();
        result.setCode(RESULT_CODE_SUCCESS);
        result.setMsg(msg);
        return result;
    }

    public static Result genSuccessResult(Object data){
        Result result = genSuccessResult();
        result.setData(data);
        return result;
    }

    public static Result genFailResult(String msg){
        Result result = new Result();
        result.setCode(RESULT_CODE_SERVER_ERROR);
        result.setMsg(msg);
        return result;
    }
}
