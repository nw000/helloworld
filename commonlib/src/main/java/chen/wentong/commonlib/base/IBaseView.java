package chen.wentong.commonlib.base;

/**
 * Created by wentong.chen on 18/1/25.
 * 功能：
 */
public interface IBaseView {

    void setPresenter(BasePresenter<? extends IBaseView> basePresenter);

}
