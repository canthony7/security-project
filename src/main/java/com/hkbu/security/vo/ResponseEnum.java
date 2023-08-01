package com.hkbu.security.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Chet
 * @date 10/2/2023 3:04 am
 */
@AllArgsConstructor
@Getter
@ToString
public enum ResponseEnum {
    SUCCESS(200, "成功"),
    FAIL(400, "失败"),

    DELETE_ERROR(40001, "删除失败"),

    ADD_SHOP_ERROR(40002, "添加商店失败"),

    LOGIN_ERROR(40003, "登录失败"),

    TOKEN_ERROR(40004, "token_expire"),

    EMPTY_TOKEN(40005, "token为空"),

    NOT_EXIST_ERROR(40006, "用户名或密码错误"),

    ADD_GOODS_ERROR(40007, "添加商品失败"),

    CANCEL_ORDER_ERROR(40008, "订单状态为已取消"),

    ORDER_ERROR(40009, "下单错误"),

    ILLEGAL_TOKEN_ERROR(40010, "token非法"),

    REQUEST_FORBID_ERROR(4003, "您没有访问该资源的权限");

    private final Integer code;
    private final String message;

}
