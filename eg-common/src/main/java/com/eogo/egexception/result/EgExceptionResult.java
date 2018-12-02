package com.eogo.egexception.result;

import com.eogo.egexception.status.EgExceptionStatus;
import lombok.Data;

/**
 * 异常显示结果，前端页面显示
 */
@Data
public class EgExceptionResult {
    private int status ;
    private String message ;
    private Long timestamp ;

    public EgExceptionResult() {
    }

    public EgExceptionResult(EgExceptionStatus egExceptionStatus) {
        this.status = egExceptionStatus.getCode();
        this.message = egExceptionStatus.getMessage();
        this.timestamp = System.currentTimeMillis();
    }
}
