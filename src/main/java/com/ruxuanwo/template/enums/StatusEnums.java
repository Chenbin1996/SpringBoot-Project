package com.ruxuanwo.template.enums;

import lombok.Getter;

/**
 * 状态相关枚举类
 *
 * @author ruxuanwo
 */
@Getter
public enum StatusEnums {

    ENABLE(1, "启用"),
    DISABLE(0, "禁用");

    private Integer code;
    private String name;

    StatusEnums(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String findNameByCode(Integer code){
        if (code == null){
            return null;
        }
        for (StatusEnums statusEnums : StatusEnums.values()) {
            if (statusEnums.getCode().equals(code)){
                return statusEnums.getName();
            }
        }
        return null;
    }


}
