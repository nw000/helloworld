package chen.wentong.commonlib.widget.loadsir.callback;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wentong.chen on 18/2/3.
 * 功能：被绑定的根布局的上下文
 */

public class TargetContext {
    private Context mContext;
    private ViewGroup mParentView;                //targetView 的 parent
    private int mChildIndex;
    private View mContentView;               //需要绑定的控件

    /**
     *
     * @param target fragment, view, activity
     */
    public TargetContext(Object target) {
        init(target);
    }

    private void init(Object target) {
        if (target instanceof View) {
            parseView((View) target);
        } else if (target instanceof Activity) {
            parseActivity((Activity) target);
        } else if (target instanceof Fragment) {
            parseFragment((Fragment) target);
        } else {
            throw new IllegalArgumentException("target must be view Activity fragment");
        }
        if (mParentView != null && mContentView == null) {
            mChildIndex = 0;
            mContentView = mParentView.getChildAt(mChildIndex);
        }
        if (mParentView != null && mContentView != null) {
            mParentView.removeView(mContentView);
        }
    }

    private void parseView(View target) {
        mContext = target.getContext();
        mContentView = target;
        mParentView = (ViewGroup) mContentView.getParent();
        if (mParentView != null) {
            for (int i = 0; i < mParentView.getChildCount(); i++) {
                if (mParentView.getChildAt(i) == mContentView) {
                    mChildIndex = i;
                    break;
                }
            }
        }
    }

    private void parseActivity(Activity target) {
        mContext = target.getBaseContext();
        mParentView = target.findViewById(android.R.id.content);
    }

    private void parseFragment(Fragment fragment) {
        mContext = fragment.getActivity();
        mParentView = (ViewGroup) fragment.getView();
    }

    public Context getContext() {
        return mContext;
    }

    public View getContentView() {
        return mContentView;
    }

    public ViewGroup getParentView() {
        return mParentView;
    }

    public int getChildIndex() {
        return mChildIndex;
    }
}
