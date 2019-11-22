package com.tinyspot.question.entity;

/**
 * @Author tinyspot
 * @Time 2019/11/15-16:59
 * 响应结果封装类
 */
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(){}

    public Result(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
