package com.duojiala.mikeboot.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 通用响应对象
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Slf4j
public class ResponseBean {

    private int code;
    private String msg;
    private Object data;

    public static int SUCCESS_CODE = 200;
    public static int ERROR_CODE = 300;

    public static String SUCCESS_MSG = "Success";
    public static String ERROR_MSG = "Error";

    public static ResponseBean success() {
        return ResponseBean.builder()
                .code(SUCCESS_CODE)
                .msg(SUCCESS_MSG)
                .build();
    }

    public static ResponseBean success(Object data) {
        return ResponseBean.builder()
                .code(SUCCESS_CODE)
                .msg(SUCCESS_MSG)
                .data(data)
                .build();
    }

    public static ResponseBean error() {
        return ResponseBean.builder()
                .code(ERROR_CODE)
                .msg(ERROR_MSG)
                .build();
    }

    public static ResponseBean error(int code, String msg) {
        return ResponseBean.builder()
                .code(code)
                .msg(msg)
                .build();
    }
}
