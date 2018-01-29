package chen.wentong.commonlib.net.subscriber;


import com.apkfuns.logutils.LogUtils;

import chen.wentong.commonlib.net.error.BaseException;
import chen.wentong.commonlib.net.error.ErrorEnum;
import io.reactivex.Observer;

/**
 * Created by ${wentong.chen} on 18/1/23.
 * 网络请求回调接口
 * @param <T>   网络请求回调成功的数据
 */
public abstract class AbstBaseSubscriber<T> implements Observer<ResponseBaseModel<T>> {
    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable t) {
        LogUtils.e("http error",t.getMessage());
        onRequestFailed(t);
    }

    @Override
    public void onNext(ResponseBaseModel<T> responseBaseModel) {
        if (!"0".equals(responseBaseModel.ret)) {
            onRequestFailed(new BaseException(ErrorEnum.OTHER_ERROR));
        }
        else {
            onRequestSuccess(responseBaseModel);
        }
    }

    protected void onRequestSuccess(ResponseBaseModel<T> responseBaseModel) {
        onRequestSuccess(responseBaseModel.getInfo());
    }

    public abstract void onRequestSuccess(T t);
    public abstract void onRequestFailed(Throwable t);
}
