package com.eogo.egexception;

import com.eogo.egexception.status.EgExceptionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 自定义异常，方便进行拦截不同的异常以返回不同的信息  里面涵盖枚举类定义的异常
 * 异常处理模块有一个异常拦截器会拦截自定义的异常
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EgException extends RuntimeException{
    private EgExceptionStatus egExceptionStatus ;

}
