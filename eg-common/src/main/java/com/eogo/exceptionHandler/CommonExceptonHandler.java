package com.eogo.exceptionHandler;


import com.eogo.egexception.EgException;
import com.eogo.egexception.result.EgExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice//默认拦截拿下controller注解的类  可以自定义的拦截范围
public class CommonExceptonHandler {
    @ExceptionHandler(EgException.class)
    public ResponseEntity<EgExceptionResult> handleException(EgException e){
        return  ResponseEntity.status(e.getEgExceptionStatus().getCode()).body(new EgExceptionResult(e.getEgExceptionStatus()));
    }
}
