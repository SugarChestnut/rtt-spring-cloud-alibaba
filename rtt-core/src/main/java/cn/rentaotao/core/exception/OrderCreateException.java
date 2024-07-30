package cn.rentaotao.core.exception;

/**
 * @author rtt
 * @date 2024/7/26 09:03
 */
public class OrderCreateException extends BaseBizException{

    private static final long serialVersionUID = 8065041110187397367L;

    public OrderCreateException(String errorMsg) {
        super(errorMsg);
    }

    public OrderCreateException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public OrderCreateException(BaseErrorCodeEnum baseErrorCodeEnum) {
        super(baseErrorCodeEnum);
    }

    public OrderCreateException(String errorCode, String errorMsg, Object... arguments) {
        super(errorCode, errorMsg, arguments);
    }

    public OrderCreateException(BaseErrorCodeEnum baseErrorCodeEnum, Object... arguments) {
        super(baseErrorCodeEnum, arguments);
    }
}
