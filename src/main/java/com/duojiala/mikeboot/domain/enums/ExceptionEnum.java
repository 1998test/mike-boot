package com.duojiala.mikeboot.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 自定义异常枚举
 */
@AllArgsConstructor
@Getter
@ToString
public enum  ExceptionEnum {

    BAD_REQUEST(301, "错误的请求"),
    NOT_FIND(302,"未找到对应数据")
    ;
    private final int code;
    private final String msg;
}
