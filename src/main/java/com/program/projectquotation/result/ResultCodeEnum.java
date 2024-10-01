package com.program.projectquotation.result;

public enum ResultCodeEnum {

    SUCCESS(200, "成功"),
    ERROR(500, "服务器错误"),
    GET_USER_SUCCESS(20000, "获取用户信息成功"),
    UPDATE_USER_SUCCESS(20001,"用户信息更新成功"),
    GET_VIDEO_SUCCESS(20002,"获取视频成功"),
    UPLOAD_VIDEO_SUCCESS(20003,"上传视频成功"),
    GET_CATEGORY_SUCCESS(20004,"获取产品种类成功"),
    CREATE_CATEGORY_SUCCESS(20005,"新建产品种类成功"),
    UPDATE_CATEGORY_SUCCESS(20006,"更新产品种类成功"),
    DELETE_CATEGORY_SUCCESS(20007,"删除产品种类成功"),
    GET_PRODUCT_SUCCESS(20008,"获取产品成功"),
    GET_PRODUCT_DETAIL_SUCCESS(20009,"获取产品详情成功"),

    USER_NOT_FOUND(40000, "用户不存在"),
    VIDEO_NOT_FOUND(40002, "视频不存在"),
    PRODUCT_NOT_FOUND(40008, "暂无相关产品"),

    GET_USER_ERROR(50000, "获取用户信息失败"),
    UPDATE_USER_ERROR(50001,"用户信息更新失败"),
    GET_VIDEO_ERROR(50002,"获取视频失败"),
    UPLOAD_VIDEO_ERROR(50003,"上传视频失败"),
    GET_CATEGORY_ERROR(50004,"获取产品种类失败"),
    CREATE_CATEGORY_ERROR(50005,"新建产品种类失败"),
    UPDATE_CATEGORY_ERROR(50006,"更新产品种类失败"),
    DELETE_CATEGORY_ERROR(50007,"删除产品种类失败"),
    GET_PRODUCT_ERROR(50008,"获取产品失败"),
    GET_PRODUCT_DETAIL_ERROR(50009,"获取产品详情失败"),
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
