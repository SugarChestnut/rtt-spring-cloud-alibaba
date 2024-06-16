package cn.rentaotao.customer.web;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author rtt
 * @date 2023/9/6 16:01
 */
@RestControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler(Exception.class)
    public String exception() {
        return "fail";
    }
}
