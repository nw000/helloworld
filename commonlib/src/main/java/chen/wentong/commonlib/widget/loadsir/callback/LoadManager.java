package chen.wentong.commonlib.widget.loadsir.callback;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wentong.chen on 18/2/3.
 * 功能：设置绑定的 targetView
 * 和targetView 绑定的callBack
 */

public class LoadManager {
    private static LoadManager mInstance;
    private Builder mBuilder;

    private LoadManager() {
        mBuilder = new Builder();
    }

    public static LoadManager getDefault() {
        if (mInstance == null) {
            synchronized (LoadManager.class) {
                if (mInstance == null) {
                    mInstance = new LoadManager();
                }
            }
        }
        return mInstance;
    }

    public static Builder beginBuilder() {
        return new Builder();
    }

    private LoadManager setBuilder(Builder builder) {
        this.mBuilder = builder;
        return this;
    }

    /**
     *
     * @param target 待绑定的view 或 Activity 或 fragment
     */
    public LoadService register(Object target) {
        return register(target, null);
    }

    public LoadService register(Object target, ReloadListener reloadListener) {
        return register(target, reloadListener, null);
    }

    public LoadService register(@NonNull Object target, ReloadListener reloadListener, Convertor convertor) {
        TargetContext targetContext = new TargetContext(target);
        return new LoadService(targetContext, reloadListener, convertor, mBuilder);
    }

    public static class Builder {
        private Class<? extends Callback> defaultCallback;
        private Set<Callback> mCallbacks;

        public Builder() {
            mCallbacks = new HashSet<>();
        }

        public Builder setDefaultCallback(Class<? extends Callback> callback) {
            this.defaultCallback = callback;
            return this;
        }

        public Builder addCallback(@NonNull Callback callback) {
            mCallbacks.add(callback);
            return this;
        }

        public Builder addCallbacks(@NonNull Callback ... callbacks) {
            mCallbacks.addAll(Arrays.asList(callbacks));
            return this;
        }

        Set<Callback> getCallbacks() {
            return mCallbacks;
        }

        public LoadManager builder() {
            return new LoadManager().setBuilder(this);
        }

        public void commit() {
            getDefault().setBuilder(this);
        }
    }
}
