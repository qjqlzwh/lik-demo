package com.lik.exception;

import com.lik.resp.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获 Exception 异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public R error(Exception e) {
        log.error("数据异常", e);
        e.printStackTrace();
        return R.error(e.getMessage());
    }

    /**
     * 捕获 IllegalArgumentException
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public R error(IllegalArgumentException e) {
        log.error("数据异常", e);
        e.printStackTrace();
        return R.error().msg(e.getMessage());
    }

    /**
     * 捕获 CowException
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(CowException.class)
    public R error(CowException e) {
        log.error("数据异常", e);
        e.printStackTrace();
        return R.error().msg(e.getMessage());
    }

}
