package chen.wentong.commonlib.widget.loadsir.callback;

import android.content.Context;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import chen.wentong.commonlib.utils.IOUtil;
import chen.wentong.commonlib.widget.loadsir.LoadLayout;

/**
 * Created by wentong.chen on 18/2/3.
 * 功能：控制callback 的显示和隐藏
 */

public class LoadService {
    private TargetContext mTargetContext;
    private ReloadListener mReloadListener;
    private Convertor mConvertor;
    private LoadLayout mLoadLayout;
    private SuccessCallback mCurCallback;
    private Class<? extends Callback> mPreCallback;
    private Map<Class<? extends Callback>, Callback> mCallbackMap;

    /**
     *
     * @param targetContext 绑定布局的上下文
     * @param reloadListener 重试监听
     * @param convertor 转换状态的回调
     * @param builder 需要被注册的callback
     * 初始化布局
     */
    public LoadService(TargetContext targetContext, ReloadListener reloadListener,
                       Convertor convertor, LoadManager.Builder builder) {
        this.mTargetContext = targetContext;
        this.mReloadListener = reloadListener;
        this.mConvertor = convertor;
        mCallbackMap = new HashMap<>();
//        mLoadLayout = new LoadLayout(targetContext.getContext(), reloadListener);
        //创建根布局
        mLoadLayout = getRootLayout(mTargetContext.getContext());
        mLoadLayout.setLayoutParams(mTargetContext.getContentView().getLayoutParams());
        mTargetContext.getParentView().addView(mLoadLayout, mTargetContext.getChildIndex());
        setupSuccessCallback(new SuccessCallback());
        //将builder中的callback复制一份保存下来， 不要直接引用builder的callback防止造成内存泄露
        for (Callback callback : builder.getCallbacks()) {
            setupcallback(callback);
        }
    }

    /**
     * 添加callback
     * @param callback
     */
    private void addCallback(Callback callback) {
        mCallbackMap.put(callback.getClass(), callback);
    }

    /**
     * 初始化callback
     * @param callback
     */
    private void setupcallback(Callback callback) {
        try {
            Callback cloneCallback = IOUtil.copy(callback);
            cloneCallback.setupCallback(null, mTargetContext.getContext(), mReloadListener);
            addCallback(cloneCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化加载成功的callback
     */
    private void setupSuccessCallback(SuccessCallback callback) {
        callback.setupCallback(mTargetContext.getContentView(), mTargetContext.getContext(), mReloadListener);
        addCallback(callback);
        showCallback(callback.getClass());
    }

    /**
     * 显示指定的callback
     * @param aCallback
     */
    public View showCallback(Class<? extends Callback> aCallback) {
        //移除之前添加的view
        if (mPreCallback != null) {
            if (mPreCallback == aCallback) {
                return mCallbackMap.get(mPreCallback).getRootView();
            }
            mCallbackMap.get(mPreCallback).onDetach();
            mLoadLayout.removeView(mCallbackMap.get(mPreCallback).getRootView());
        }
        //添加callback到loadlayout, 并显示
        Callback callback = mCallbackMap.get(aCallback);
        if (callback != null) {
            mLoadLayout.addView(callback.getRootView());
            callback.show();
            mPreCallback = aCallback;
            return callback.getRootView();
        } else {
            throw new IllegalArgumentException("this callback not register, please register first");
        }
    }

    public LoadLayout getRootLayout(Context context) {
        return new LoadLayout(context);
    }
}
