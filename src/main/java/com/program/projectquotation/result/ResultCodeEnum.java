package com.program.projectquotation.result;

public enum ResultCodeEnum {

    SUCCESS(200, "成功"),
    ERROR(500, "服务器错误"),
    GET_USER_SUCCESS(20000, "获取用户信息成功"),
    UPDATE_USER_SUCCESS(20001,"用户信息更新成功"),
    GET_VIDEO_SUCCESS(20002,"获取视频成功"),
    UPLOAD_VIDEO_SUCCESS(20003,"上传视频成功"),
    GET_CATEGORY_SUCCESS(20004,"获取商品目录成功"),
    CREATE_CATEGORY_SUCCESS(20005,"新建商品目录成功"),
    UPDATE_CATEGORY_SUCCESS(20006,"更新商品目录成功"),
    DELETE_CATEGORY_SUCCESS(20007,"删除商品目录成功"),
    GET_PRODUCT_SUCCESS(20008,"获取商品成功"),
    CREATE_PRODUCT_SUCCESS(20009,"创建商品成功"),
    UPDATE_PRODUCT_SUCCESS(20010,"更新商品成功"),
    DELETE_PRODUCT_SUCCESS(20011,"删除商品成功"),
    GET_PRODUCT_DETAIL_SUCCESS(20012,"获取商品详情成功"),
    CREATE_ORDER_SUCCESS(20013,"创建订单成功"),
    GET_ORDER_SUCCESS(20014,"获取订单成功"),
    CREATE_PRODUCT_IMAGE_SUCCESS(20015,"创建商品图片成功"),
    DELETE_PRODUCT_IMAGE_SUCCESS(20016,"删除商品图片成功"),
    CREATE_PRODUCT_SPEC_SUCCESS(20017,"创建商品规格成功"),
    DELETE_PRODUCT_SPEC_SUCCESS(20018,"删除商品规格成功"),
    UPDATE_PRODUCT_SPEC_SUCCESS(20019,"更新商品规格成功"),
    CREATE_PRODUCT_OPTIONS_SUCCESS(20020,"创建商品选项成功"),
    DELETE_PRODUCT_OPTIONS_SUCCESS(20021,"删除商品选项成功"),
    UPDATE_PRODUCT_OPTIONS_SUCCESS(20022,"更新商品选项成功"),

    DELETE_VIDEO_SUCCESS(20023,"删除视频成功"),
    TIME_UPDATE_SUCCESS(20024,"上新时间更新成功"),

    USER_NOT_FOUND(40000, "用户不存在"),
    VIDEO_NOT_FOUND(40002, "视频不存在"),
    PRODUCT_NOT_FOUND(40008, "暂无相关商品"),
    ORDER_NOT_FOUND(40011, "商品详情不存在"),

    GET_USER_ERROR(50000, "获取用户信息失败"),
    UPDATE_USER_ERROR(50001,"用户信息更新失败"),
    GET_VIDEO_ERROR(50002,"获取视频失败"),
    UPLOAD_VIDEO_ERROR(50003,"上传视频失败"),
    GET_CATEGORY_ERROR(50004,"获取商品目录失败"),
    CREATE_CATEGORY_ERROR(50005,"新建商品目录失败"),
    UPDATE_CATEGORY_ERROR(50006,"更新商品目录失败"),
    DELETE_CATEGORY_ERROR(50007,"删除商品目录失败"),
    GET_PRODUCT_ERROR(50008,"获取商品失败"),
    CREATE_PRODUCT_ERROR(50009,"创建商品失败"),
    UPDATE_PRODUCT_ERROR(50010,"更新商品失败"),
    DELETE_PRODUCT_ERROR(50011,"删除商品失败"),
    GET_PRODUCT_DETAIL_ERROR(50012,"获取商品详情失败"),
    CREATE_ORDER_ERROR(50013,"创建订单失败"),
    GET_ORDER_ERROR(50014,"获取订单失败"),
    CREATE_PRODUCT_IMAGE_ERROR(50015,"创建商品图片失败"),
    DELETE_PRODUCT_IMAGE_ERROR(50016,"删除商品图片失败"),
    CREATE_PRODUCT_SPEC_ERROR(50017,"创建商品规格失败"),
    DELETE_PRODUCT_SPEC_ERROR(50018,"删除商品规格失败"),
    UPDATE_PRODUCT_SPEC_ERROR(50019,"更新商品规格失败"),
    CREATE_PRODUCT_OPTIONS_ERROR(50020,"创建商品选项失败"),
    DELETE_PRODUCT_OPTIONS_ERROR(50021,"删除商品选项失败"),
    UPDATE_PRODUCT_OPTIONS_ERROR(50022,"更新商品选项失败"),

    DELETE_VIDEO_ERROR(50023,"删除视频失败"),
    TIME_UPDATE_ERROR(50024,"上新时间更新失败"),
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
