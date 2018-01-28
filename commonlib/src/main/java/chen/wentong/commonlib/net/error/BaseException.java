package chen.wentong.commonlib.net.error;

/**
 * Created by wentong.chen on 18/1/26.
 * 功能：网络异常基类
 */

public class BaseException extends Exception {

    private ErrorEnum mErrorEnum;

    public BaseException(ErrorEnum errorEnum) {
        super(errorEnum.getMsg());
        this.mErrorEnum = errorEnum;
    }

    @Override
    public String getMessage() {
        if (mErrorEnum != null) return mErrorEnum.getMsg();
        return super.getMessage();
    }

    public int getCode() {
        if (mErrorEnum != null) {
            mErrorEnum.getCode();
        }
        return ErrorEnum.NO_DATA_ERROR.getCode();
    }
}
