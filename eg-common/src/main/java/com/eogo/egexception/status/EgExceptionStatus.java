package com.eogo.egexception.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 定义项目中的异常 包括状态码  ，和返回信息
 * 提供构造函数以及get方法
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum EgExceptionStatus {
    PARAMS_CANNOT_BE_NULL(400,"参数不能为空,请检查参数")
    ;
    private int code ;
    private String message ;
}
