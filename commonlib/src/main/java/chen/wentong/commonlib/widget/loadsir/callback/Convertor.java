package chen.wentong.commonlib.widget.loadsir.callback;

/**
 * Created by wentong.chen on 18/2/3.
 * 功能：将请求的数据根据 根据状态不同转为返回不同 callback
 */

public interface Convertor<T> {

    Class<? extends Callback> convert(T t);
}
