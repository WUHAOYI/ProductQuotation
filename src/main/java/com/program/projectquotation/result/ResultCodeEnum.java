package com.program.projectquotation.result;

public enum ResultCodeEnum {

    SUCCESS(200, "成功"),
    ERROR(500, "服务器错误"),
    GET_USER_SUCCESS(20000, "获取用户信息成功"),
    GET_CATEGORY_SUCCESS(20001,"获取产品种类成功"),


    USER_NOT_FOUND(40000, "用户不存在"),

    GET_USER_ERROR(50000, "获取用户信息失败"),
    GET_CATEGORY_ERROR(50001,"获取产品种类失败")
    ;
    private Integer code;
    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
