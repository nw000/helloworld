package chen.wentong.commonlib.player.controler;

/**
 * Created by wentong.chen on 18/2/6.
 * 功能：
 */

public class Songinfo<T> {
    private String url;
    private T t;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
