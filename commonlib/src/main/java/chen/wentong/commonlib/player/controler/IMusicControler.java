package chen.wentong.commonlib.player.controler;

import java.util.List;
import java.util.Set;

import chen.wentong.commonlib.player.media.IPlayer;

/**
 * Created by wentong.chen on 18/2/5.
 * 功能：
 * 1、播放\暂停、停止
 2、上一曲
 3、下一曲
 4、播放模式（随机，单曲循环， 列表循环）
 5、歌曲列表
 6、设置播放路径（url， file）
 7、退出播放器（资源回收）
 8、当前播放进度
 9、拖动播放
 T 歌曲
 */

public interface IMusicControler<T> {
    /**
     * 播放/暂停
     * @param t 播放的歌曲
     */
    void play(T t);

    /**
     * 播放索引
     * @param index
     */
    void play(int index);

    /**
     * 上一曲
     */
    void preSong();

    /**
     * 下一曲
     */
    void next();

    /**
     * 设置播放模式
     */
    void setPlaymode(PlayMode playMode);

    /**
     * 设置播放列表歌曲
     * @param songList
     */
    void setSongList(List<T> songList);

    /**
     * 释放资源
     */
    void release();

    /**
     * 获取当前播放位置
     * @return 获取当前播放位置
     */
    long getCurrentPosition();

    /**
     * 拖动的指定位置
     * @param position 设置播放位置
     */
    void seekTo(int position);

    /**
     * 获取当前的播放器
     * @return
     */
    IPlayer getPlayer();

    /**
     * 当前是否处于播放状态
     * @return
     */
    boolean isPlaying();
}
