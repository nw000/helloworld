package chen.wentong.commonlib.player.media;

/**
 * Created by wentong.chen on 18/2/5.
 * 功能：音乐播放定义的接口
 */

public interface IPlayer {
    /**
     * 初始化播放器
     */
    void initPlayer();

    /**
     * 开始播放
     * @param url 音乐资源路径
     */
    void start(String url);

    /**
     * 继续上次的播放
     */
    void continueStart();

    /**
     * 设置播放位置
     * @param position
     */
    void seekTo(int position);

    /**
     * 暂停播放
     */
    void pause();

    /**
     * 停止播放
     */
    void stop();

    /**
     * 重置初始状态
     */
    void reset();

    /**
     * 释放资源
     */
    void release();

    /**
     * 获取当前音乐播放的位置
     * @return
     */
    int getCurrentPosition();

    /**
     * 获取音乐当前播放状态
     * @return
     */
    MediaStatus getCurrentStatus();

    /**
     * 设置是否循环播放
     * @param looping
     */
    void setLooping(boolean looping);

    /**
     * 是否处于播放状态
     * @return
     */
    boolean isPlaying();
}
