package com.ruxuanwo.template.enums;

/**
 * 结果返回码
 *
 * @author ruxuanwo
 */
public enum ResultEnum {
    SUCCESS(200, "Success!", "OK"),
    SERVER_ERRO(500, "Internal Server Error", "服务器发生了未知的错误，请重试或联系管理员"),
    BAD_REQUEST(400, "Bad Request", "客户端请求有语法错误，不能被服务器所理解"),
    UNAUTHORIZED(401, "Unauthorized", "权限不足"),
    FORBIDDEN(403, "Forbidden", "请求未经授权"),
    NOT_FOUND(404, "Not Found", "未找到请求资源"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed", "客户端请求中的方法被禁止"),
    NOT_ACCEPTABLE(406, "Not Acceptable", "服务器无法根据客户端请求的内容特性完成请求"),
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required", "请求要求代理的身份认证，与401类似，但请求者应当使用代理进行授权"),
    REQUEST_TIMEOUT(408, "Request Time-out", "服务器等待客户端发送的请求时间过长，超时"),
    REQUEST_ENTITY_TOO_LARGE(413, "Request Entity Too Large", "由于请求的实体过大，服务器无法处理，因此拒绝请求。为防止客户端的连续请求，服务器可能会关闭连接。如果只是服务器暂时无法处理，则会包含一个Retry-After的响应信息"),
    REQUESTURI_TOO_LARGE(414, "Request-URI Too Large", "请求的URI过长（URI通常为网址），服务器无法处理"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type", "服务器无法处理请求附带的媒体格式"),
    LOGIN_SUCCESS(101, "Login Success", "登录成功"),
    NOT_LOGIN(102, "NOT Login", "未登录");
    private Integer code;
    private String title;
    private String msg;

    ResultEnum(Integer code, String title, String msg) {
        this.code = code;
        this.title = title;
        this.msg = msg;
    }

    public int code() {
        return code;
    }

    public String title() {
        return title;
    }

    public String msg() {
        return msg;
    }

    public static ResultEnum valueOf(int code) {
        for (ResultEnum httpStatus : values()) {
            if (httpStatus.code() == code) {
                return httpStatus;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }
}
