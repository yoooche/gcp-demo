package com.eight.demo.module.common.error;

import com.eight.demo.module.common.constant.StatusCode;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseExceptionResponse implements Serializable {

    private String error;
    private String message;

    public static BaseExceptionResponse of(BaseException e) {
        return new BaseExceptionResponse(e.getStatusCode(), e.getMessage());
    }

    public static BaseExceptionResponse of(Exception e) {
        return new BaseExceptionResponse(StatusCode.UNKNOW_ERR, e.getMessage());
    }

}
