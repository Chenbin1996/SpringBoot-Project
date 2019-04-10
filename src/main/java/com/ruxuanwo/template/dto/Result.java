package com.ruxuanwo.template.dto;

import com.ruxuanwo.template.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 结果集
 *
 * @author ruxuanwo
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String title;
    private String msg;
    private T data;
    private Date timestamp = new Date();
    public Result(ResultEnum resultEnum){
        this.code = resultEnum.code();
        this.title = resultEnum.title();
        this.msg = resultEnum.msg();
    }
    public Result(ResultEnum resultEnum, T data){
        this.code = resultEnum.code();
        this.title = resultEnum.title();
        this.msg = resultEnum.msg();
        this.data = data;
    }
    public Result(Integer code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(){

    }
}
