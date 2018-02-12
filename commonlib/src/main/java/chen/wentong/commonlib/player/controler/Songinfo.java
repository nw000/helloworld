package chen.wentong.commonlib.player.controler;

/**
 * Created by wentong.chen on 18/2/6.
 * 功能：歌曲信息类
 */

public class Songinfo<T> {
    protected String url;
    protected String singer;
    protected String name;
    protected T t;

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

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Songinfo{" +
                "url='" + url + '\'' +
                ", singer='" + singer + '\'' +
                ", name='" + name + '\'' +
                ", t=" + t +
                '}';
    }
}
