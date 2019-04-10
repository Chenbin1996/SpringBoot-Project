package com.ruxuanwo.template.exception;

/**
 * 自定义参数为空异常
 *
 * @author ruxuanwo
 */
public class ParamIsNullException extends RuntimeException {
    private final String parameterName;
    private final String parameterType;
    private final String message;

    public ParamIsNullException(String parameterName, String parameterType, String message) {
        super();
        this.parameterName = parameterName;
        this.parameterType = parameterType;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "请求参数类型：" + this.parameterType + "，参数名： \'" + this.parameterName + message;
    }

    public final String getParameterName() {
        return this.parameterName;
    }

    public final String getParameterType() {
        return this.parameterType;
    }
}
