package cn.itfield.wxcc.exception;

import lombok.Data;

//全局异常
@Data
public class GlobalException extends RuntimeException{

    /**--------------------------------------------------------
     传一个错误信息给异常对象
     --------------------------------------------------------**/
    public GlobalException(String message){
        super(message);
    }
}