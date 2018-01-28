package chen.wentong.commonlib.net.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import io.reactivex.Observable;
import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 网络请求代理类 Created by ${wentong.chen} on 18/1/24.
 * @param <T> 代理对象
 */
public final class NetInvocationHandler<T> implements InvocationHandler {
    private T actualService;

    public NetInvocationHandler(T var1) {
        this.actualService = var1;
    }

    public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
        Object result = method.invoke(this.actualService, args);
        if (result != null && result instanceof Observable) {
            Observable observable = (Observable) result;
            observable = observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .lift(new ObservableOperator() {
                        @Override
                        public Observer apply(Observer observer) throws Exception {
                            return observer;
                        }
                    });

            return observable;
        } else {
            return result;
        }
    }
}