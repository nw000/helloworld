package chen.wentong.commonlib.widget.loadsir.callback;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;

import java.io.Serializable;

/**
 * Created by wentong.chen on 18/2/3.
 * 功能：不同状态下，根据layoutid填充的view ，
 */

public abstract class Callback implements Serializable {
    private Context mContext;
    private ReloadListener mReloadListener;
    private View mRootView;
    private boolean isInit;
    private boolean mVisibility;                         //显示状态

    public Callback() {

    }

    /**
     *
     * @param view 当前状态的rootview
     * @param context
     * @param reloadListener 重新加载的监听
     */
    public Callback(View view, Context context, ReloadListener reloadListener) {
        setupCallback(view, context, reloadListener);
    }

    public void setupCallback(View view, Context context, ReloadListener reloadListener) {
        this.mRootView = view;
        this.mContext = context;
        this.mReloadListener = reloadListener;
    }

    /**
     * 设置布局id
     * @return
     */
    protected abstract  @LayoutRes int createView();

    /**
     * 如果不设置id， 可以通过这个方法返回rootview
     * @return
     */
    protected View onBuildView() {
        return null;
    }

    public View getRootView() {
        if (isInit && mRootView != null) {
            return mRootView;
        }
        if (onBuildView() != null) {
            mRootView = onBuildView();
        } else if (createView() > 0) {
            mRootView = View.inflate(mContext, createView(), null);
        }
        if (mRootView == null) {
            throw new NullPointerException("you must set rootView id or view");
        }
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mReloadListener != null) {
                    mReloadListener.onReload(v);
                }
            }
        });
        isInit = true;
        onViewCreate(mRootView);
        return mRootView;
    }

    public void show() {
        setViewVisibility(View.VISIBLE);
    }

    public void hide() {
        setViewVisibility(View.GONE);
    }

    public void setViewVisibility(int visibility) {
        this.mVisibility = View.VISIBLE == visibility;
        mRootView.setVisibility(this.mVisibility ? View.VISIBLE : View.GONE);
    }

    /**
     * 当rootview创建完成
     * @param view
     */
    public void onViewCreate(View view) {

    }

    /**
     * 当view被绑定
     * @param view
     */
    public void onAttach(View view) {

    }

    /**
     * 当view 被移除
     */
    public void onDetach() {

    }
}
