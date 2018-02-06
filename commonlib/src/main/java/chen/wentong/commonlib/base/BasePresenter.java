package chen.wentong.commonlib.base;


import android.arch.lifecycle.Lifecycle;

import com.trello.rxlifecycle2.LifecycleProvider;

public abstract  class BasePresenter<T extends IBaseView> {
    public final String TAG = getClass().getSimpleName();

    /**
     * 绑定生命周期请求请加上.compose(provider.bindToLifecycle())
     */
    protected LifecycleProvider<Lifecycle.Event> mProvider;
    protected T mView;

    public BasePresenter(T view, LifecycleProvider<Lifecycle.Event> provider) {
        this.mProvider = provider;
        this.mView = view;
    }

    public void onDestroy() {
        mView = null;
    }


//    /**
//     * 耗时请求绑定生命周期
//     * @param observable
//     * @param <T>
//     * @return
//     */
//    public <T> Observable<T> bindLifeCycle(Observable<T> observable) {
//        return observable
//                .compose(mProvider.bindToLifecycle());
//
//    }
}
