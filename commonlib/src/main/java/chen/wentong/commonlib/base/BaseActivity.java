package chen.wentong.commonlib.base;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by ${wentong.chen} on 18/1/23.
 */

public abstract class BaseActivity extends RxLifeActivity implements IBaseView{
    public final String TAG = getClass().getSimpleName() + "%s";
    //生命周期绑定
    protected final LifecycleProvider<Lifecycle.Event> mLifecycleProvider
            = AndroidLifecycle.createLifecycleProvider(this);
    protected List<BasePresenter> mPresenterList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        initListener();
        initEvent();
        initData();
    }

    /**
     * 设置布局id
     * @return
     */
    protected abstract  @LayoutRes int getLayoutId();

    protected abstract void initView();

    protected void initListener() {

    }

    /**
     * 添加一些事件监听，如：登录成功后首页状态更新
     */
    protected void initEvent() {

    }

    protected abstract void initData();

    public void showLongToast(CharSequence toast) {
        if (!TextUtils.isEmpty(toast)) {
            Toast.makeText(getBaseContext(), toast, Toast.LENGTH_SHORT).show();
        }
    }

    public void startActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @Override
    public void setPresenter(BasePresenter<? extends IBaseView> basePresenter) {
        if (mPresenterList == null) {
            mPresenterList = new ArrayList<>();
        }
        mPresenterList.add(basePresenter);
    }

    @Override
    protected void onDestroy() {
        if (mPresenterList != null && mPresenterList.size() > 0) {
            for (BasePresenter basePresenter : mPresenterList) {
                basePresenter.onDestroy();
            }
        }
        super.onDestroy();
    }
}
