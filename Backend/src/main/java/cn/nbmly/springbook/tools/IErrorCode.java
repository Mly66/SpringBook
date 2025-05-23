package cn.nbmly.springbook.tools;

/**
 * 封装API的错误码
 */
public interface IErrorCode {
    /**
     * 获取错误码
     */
    Integer getCode();

    /**
     * 获取错误信息
     */
    String getMessage();
}