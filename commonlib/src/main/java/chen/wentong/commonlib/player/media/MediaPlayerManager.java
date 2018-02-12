package chen.wentong.commonlib.player.media;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * Created by wentong.chen on 18/2/5.
 * 功能：音乐播放管理者
 */

public class MediaPlayerManager implements IPlayer{
    private static final String TAG = MediaPlayerManager.class.getSimpleName();
    private MediaPlayer mMediaPlayer;
    private MediaStatus mMediaStatus = MediaStatus.NO_INITIALIZED;                          //音乐播放状态
    private String url;
    private boolean mLooping;                                                     //是否循环播放
    private MediaPlayerListener mMediaPlayerListener;                             //音乐播放监听

    @Override
    public void initPlayer() {
        if (mMediaPlayer != null) {
            release();
        }
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            initListener();
        }
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    private void initListener() {
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                LogUtils.d(TAG, "onPrepared");
                EventBus.getDefault().post(MediaEvent.PREPARE);
                onStatusChange();
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                LogUtils.d(TAG, "onCompletion");
                mMediaStatus = MediaStatus.COMPLETE;
                EventBus.getDefault().post(MediaEvent.ON_COMPLETE);
                onStatusChange();
            }
        });
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                LogUtils.d(TAG, "onCompletion");
                mMediaStatus = MediaStatus.ERROR;
                EventBus.getDefault().post(MediaEvent.ON_ERROR);
                onStatusChange();
                return false;
            }
        });
        mMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                LogUtils.d(TAG, "onSeekComplete");
                EventBus.getDefault().post(MediaEvent.SEEK_TO_COMPLITE);
            }
        });
        mMediaStatus = MediaStatus.INITIALIZED;
    }

    /**
     * 开始播放音乐
     */
    @Override
    public void start(String url) {
        this.url = url;
        if (TextUtils.isEmpty(url)) {
            mMediaStatus = MediaStatus.PATH_ERROR;

            return;
        }
        try {
            initPlayer();
            reset();
            //设置流媒体数据类型
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            LogUtils.d(TAG, "music is start");
            mMediaStatus = MediaStatus.PLAYING;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.d(TAG, "music start error:" + e.getMessage());
        }
    }

    /**
     * 继续上次的位置播放音乐
     */
    @Override
    public void continueStart() {
        if (mMediaStatus == MediaStatus.PAUSE) {
            mMediaPlayer.start();
            mMediaStatus = MediaStatus.PLAYING;
            LogUtils.d(TAG, "continueStart.....");
        }
    }

    @Override
    public void seekTo(int position) {
        if (mMediaStatus ==  MediaStatus.STOP) {
            try {
                mMediaPlayer.prepare();
                continueStart();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mMediaStatus == MediaStatus.PLAYING ||
                mMediaStatus == MediaStatus.PAUSE) {
            mMediaPlayer.seekTo(position);
            LogUtils.d(TAG, "seekTo.....position = " + position);
        }
    }

    /**
     * 暂停音乐播放
     */
    public void pause() {
        if (MediaStatus.PLAYING == mMediaStatus) {
            mMediaPlayer.pause();
            mMediaStatus = MediaStatus.PAUSE;
            LogUtils.d(TAG, "pause ... position = " + mMediaPlayer.getCurrentPosition());
        }
    }

    /**
     * 停止音乐播放
     */
    public void stop() {
        if (mMediaStatus == MediaStatus.PREPARE || mMediaStatus == MediaStatus.PLAYING ||
                mMediaStatus == MediaStatus.PAUSE) {
            mMediaPlayer.stop();
            mMediaStatus = MediaStatus.PAUSE;
            LogUtils.d(TAG, "stop");
        }
    }

    @Override
    public void reset() {
        if (mMediaStatus != MediaStatus.NO_INITIALIZED) {
            mMediaPlayer.reset();
            mMediaStatus = MediaStatus.INITIALIZED;
            LogUtils.d(TAG, "reset");
        }
    }

    @Override
    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mMediaStatus = MediaStatus.NO_INITIALIZED;
        LogUtils.d(TAG, "release");
    }

    @Override
    public int getCurrentPosition() {
        int position = 0;
        if (mMediaStatus == MediaStatus.PLAYING || mMediaStatus == MediaStatus.PAUSE) {
            position = mMediaPlayer.getCurrentPosition();
        }
        LogUtils.d(TAG, "CurrentPosition = " + position);
        return position;
    }

    @Override
    public MediaStatus getCurrentStatus() {
        LogUtils.d(TAG, "current music status = " + mMediaStatus.getDesc());
        return mMediaStatus;
    }

    @Override
    public void setLooping(boolean looping) {
        this.mLooping = looping;
        if (mMediaPlayer != null) {
            mMediaPlayer.setLooping(looping);
        }
    }

    public void setMediaPlayerListener(MediaPlayerListener mediaPlayerListener) {
        this.mMediaPlayerListener = mediaPlayerListener;
    }

    public boolean isPlaying() {
        return MediaStatus.PLAYING == mMediaStatus;
    }

    private void onStatusChange() {
        if (mMediaPlayerListener != null) {
            mMediaPlayerListener.onPlayerStatusChange(mMediaStatus, mMediaPlayer);
        }
    }
}
