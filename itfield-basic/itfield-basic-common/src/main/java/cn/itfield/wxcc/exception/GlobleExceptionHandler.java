package cn.itfield.wxcc.exception;

import cn.itfield.wxcc.result.JsonResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobleExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    public JsonResult globleException (GlobalException e){
        e.printStackTrace();
        return JsonResult.me().setCode(5000).setMessage("操作失败:正在殴打程序员"+e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public JsonResult Exception (Exception e){
        e.printStackTrace();
        return JsonResult.error("操作失败:服务器炸了"+e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult Exception (MethodArgumentNotValidException e){
        e.printStackTrace();
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<String> objects = new ArrayList<>();

        for (ObjectError allError : allErrors) {
            objects.add(allError.getDefaultMessage());
        }
        return JsonResult.error("操作失败:服务器炸了"+String.join(",",objects));
    }
}
