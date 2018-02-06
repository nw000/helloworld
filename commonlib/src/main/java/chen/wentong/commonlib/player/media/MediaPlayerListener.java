package chen.wentong.commonlib.player.media;

/**
 * Created by wentong.chen on 18/2/5.
 * 功能：播放状态监听
 */

public interface MediaPlayerListener {
    /**
     * 准备完成监听
     */
    void onPrepare();

    /**
     * 播放结束监听
     */
    void onComplete();

    /**
     * 播放错误监听
     * @param e
     */
    void onError(Exception e);

    /**
     * 播放进度监听
     * @param progress
     */
    void onProgressListener(long progress);
}
