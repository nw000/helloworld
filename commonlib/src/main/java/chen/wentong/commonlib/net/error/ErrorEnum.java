package chen.wentong.commonlib.net.error;

/**
 * Created by wentong.chen on 18/1/26.
 * 功能：异常枚举
 */

public enum  ErrorEnum {

    PARSE_ERROR("数据解析异常", 0),
    NO_CONNECT_ERROR("无网络异常", 1),
    AUTH_ERROR("身份验证异常", 2),
    NO_DATA_ERROR("无数据异常", 3),
    BUSINESS_ERROR("业务异常", 4),
    SERVICE_ERROR("服务器异常", 5),
    OTHER_ERROR("其他异常", 6);

    private String msg;
    private int code;

    ErrorEnum(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
