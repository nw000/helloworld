package chen.wentong.commonlib.widget.loadsir;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

import chen.wentong.commonlib.widget.loadsir.callback.Callback;
import chen.wentong.commonlib.widget.loadsir.callback.ReloadListener;

/**
 * Created by wentong.chen on 18/2/3.
 * 功能：根据callback 创建callback，并进行添加操作
 */

public class LoadLayout extends FrameLayout {
    private Context mContext;
    private ReloadListener mReloadListener;
    private Map<Class<Callback>, Callback> mCallbackMap = new HashMap<>();
    public LoadLayout(@NonNull Context context) {
        super(context);
    }

    /**
     *
     * @param context
     * @param reloadListener 重新加载的回调
     */
    public LoadLayout(@NonNull Context context, ReloadListener reloadListener) {
        this(context);
        this.mReloadListener = reloadListener;
    }

    public void showCallback(Class<Callback> callbackClass) {

    }
}
