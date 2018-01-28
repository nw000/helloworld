package chen.wentong.commonlib.net.subscriber;


import com.apkfuns.logutils.LogUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wentong.chen on 18/1/17.
 */

public abstract class AbstractObjectSubscriber<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
        LogUtils.d( "onSubscribe: ");
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }


    @Override
    public void onComplete() {
        LogUtils.d("onComplete: Over!");
    }


    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
        onFailure(t);
    }


    public abstract void onFailure(Throwable e);

    public abstract void onSuccess(T t);
}
