package github.jiangbyte.io.common.core.exception;

/**
 * 业务异常：携带业务码与提示文案，由全局异常处理转为统一 API 响应。
 *
 * Author: Charlie
 */
public class BizException extends RuntimeException {

    private final int code;

    /** 使用默认业务码 400 构造异常。 */
    public BizException(String message) {
        this(400, message);
    }

    /** 使用指定业务码与提示文案构造异常。 */
    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    /** 返回业务错误码。 */
    public int getCode() {
        return code;
    }
}
