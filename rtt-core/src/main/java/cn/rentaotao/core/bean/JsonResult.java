package cn.rentaotao.core.bean;

import cn.rentaotao.core.exception.BaseErrorCodeEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 统一的Spring mvc响应结果封装对象
 *
 */
@Data
@Slf4j
public class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请求成功
     */
    private static final boolean REQUEST_SUCCESS = true;

    /**
     * 请求失败
     */
    private static final boolean REQUEST_FAIL = false;

    /**
     * 默认错误码
     */
    private static final String DEFAULT_ERROR_CODE = "-1";

    /**
     * 请求是否成功
     */
    private Boolean success;

    /**
     * 业务数据
     */
    private T data;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误提示语
     */
    private String errorMessage;


    public JsonResult() {
    }

    public JsonResult(Boolean success, T data, String errorCode, String errorMessage) {
        this.success = success;
        this.data = data;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * 成功，不用返回数据
     */
    public static <T> JsonResult<T> buildSuccess() {
        return new JsonResult<>(REQUEST_SUCCESS, null, null, null);
    }

    /**
     * 成功，返回数据
     */
    public static <T> JsonResult<T> buildSuccess(T data) {
        return new JsonResult<>(REQUEST_SUCCESS, data, null, null);
    }

    /**
     * 失败，固定状态码
     */
    public static <T> JsonResult<T> buildError(String errorMsg) {
        return new JsonResult<>(REQUEST_FAIL, null, DEFAULT_ERROR_CODE, errorMsg);
    }

    /**
     * 失败，自定义错误码和信息
     */
    public static <T> JsonResult<T> buildError(String errorCode, String errorMsg) {
        return new JsonResult<>(REQUEST_FAIL, null, errorCode, errorMsg);
    }

    /**
     * 失败，枚举类定义错误码和信息
     */
    public static <T> JsonResult<T> buildError(BaseErrorCodeEnum baseErrorCodeEnum) {
        return new JsonResult<>(REQUEST_FAIL, null, baseErrorCodeEnum.getErrorCode(), baseErrorCodeEnum.getErrorMsg());
    }
}
