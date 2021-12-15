package com.zjgsu.online_market.error;

import com.zjgsu.online_market.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;


/**
 * @author SANZONG
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException .class)
    public Result handler(RuntimeException e)
    {
        log.error("运行时异常 :  "+e.getMessage());
        return Result.fail(400,e.getMessage(),600);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = SQLException.class)
    public Result handler(SQLException e)
    {
        log.error("sql语句错误");
        return Result.fail(400,e.getSQLState(),601);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result handler(MethodArgumentTypeMismatchException e)
    {
        log.error("参数转换错误: "+ e.getMessage());
        return Result.fail(400,e.getMessage(),602);
    }

    @ExceptionHandler(NullPointerException.class)
    public Result handler(NullPointerException e)
    {
        log.error("空指针异常"+e.getMessage());
        return Result.fail(400,e.getCause().getMessage(),603);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public Result handler(ConstraintViolationException e) {
        log.error("参数验证未通过"+e.getMessage());
        return Result.fail(400, e.getMessage(),604);
    }

    @ExceptionHandler({BindException.class})
    public Result handler(BindException e) {
        log.error("参数验证未通过"+e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        Objects.requireNonNull(bindingResult);
        return Result.fail(400, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(),604);
    }


    @ExceptionHandler(value = IOException.class)
    public Result handler(IOException e)
    {
        log.error("IO发生异常");
        return Result.fail(400,e.getMessage(),605);
    }
}
