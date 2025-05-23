package cn.nbmly.springbook.controller;

import cn.nbmly.springbook.tools.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@RestControllerAdvice
public class ExceptionController {
    /**
     * 全局异常处理
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e, HttpServletRequest request) {
        String errorInfo;

        if (e instanceof ArithmeticException) {
            errorInfo = "算术异常";
        } else if (e instanceof ArrayIndexOutOfBoundsException) {
            errorInfo = "数组下标越界异常";
        } else if (e instanceof SQLException) {
            errorInfo = "数据库访问异常";
        } else {
            errorInfo = "未知异常: " + e.getMessage();
        }

        // 记录日志
        System.err.println("异常信息: " + errorInfo);
        e.printStackTrace();

        return Result.failed(errorInfo);
    }
}
