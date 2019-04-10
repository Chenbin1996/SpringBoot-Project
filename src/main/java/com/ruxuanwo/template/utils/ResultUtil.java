package com.ruxuanwo.template.utils;


import com.ruxuanwo.template.dto.Result;
import com.ruxuanwo.template.enums.ResultEnum;

/**
 * 结果集返回
 *
 * @author ruxuanwo
 */
public class ResultUtil {
    public static Result success(){
        return new Result(ResultEnum.SUCCESS);
    }
    public static <T> Result<T> success(T data){
        return new Result(ResultEnum.SUCCESS,data);
    }
    public static <T>Result<T> success(ResultEnum resultEnum){
        return new Result(resultEnum);
    }
    public static <T>Result<T> success(ResultEnum resultEnum,T data){
        Result<T> result = new Result(resultEnum,data);
        return result;
    }
    public static <T>Result<T> success(String msg,T data){
        Result<T> result = new Result(ResultEnum.SUCCESS.code(),msg , data);
        return result;
    }
    public static <T>Result<T> error(){
        return new Result(ResultEnum.SERVER_ERRO);
    }
    public static <T>Result<T> error(String msg){
        Result<T> result = new Result(ResultEnum.SERVER_ERRO);
        result.setMsg(msg);
        return result;
    }
    public static <T>Result<T> error(ResultEnum resultEnum){
        Result<T> result = new Result(resultEnum);
        return result;
    }
    public static <T>Result<T> error(ResultEnum resultEnum,String msg){
        Result<T> result = new Result(resultEnum);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> builderResponse(int code, String msg, T data) {
        Result<T> res = new Result<>();
        res.setCode(code);
        res.setMsg(msg);
        res.setData(data);
        return res;
    }
}
