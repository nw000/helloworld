package chen.wentong.commonlib.player.media;

import android.media.MediaPlayer;

/**
 * Created by wentong.chen on 18/2/5.
 * 功能：播放状态监听
 */

public interface MediaPlayerListener {
    /**
     *
     * @param mediaStatus 播放状态
     * @param mediaPlayer 播放器
     */
    void onPlayerStatusChange(MediaStatus mediaStatus, MediaPlayer mediaPlayer);
}
