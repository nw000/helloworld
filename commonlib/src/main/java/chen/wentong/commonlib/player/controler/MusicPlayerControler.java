package chen.wentong.commonlib.player.controler;

import java.util.List;
import java.util.Random;

import chen.wentong.commonlib.player.media.IPlayer;
import chen.wentong.commonlib.player.media.MediaPlayerManager;

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
 */

public class MusicPlayerControler<T> implements IMusicControler<Songinfo<T>>{
    private IPlayer mPlayer;

    private PlayMode mPlayMode = PlayMode.LIST_CYCLE_PLAY;
    private List<Songinfo<T>> mSonglist;
    private Songinfo<T> currentSong;
    private Songinfo<T> preSong;
    private Songinfo<T> nextSong;
    private int curIndex;
    private boolean autoNext;                                       //是否自动下一曲， 不是点击下一曲
    private boolean autoPre;

    public MusicPlayerControler() {
        mPlayer = getPlayer();
        mPlayer.initPlayer();
    }

    @Override
    public void play(int index) {
        if (mSonglist == null && mSonglist.isEmpty()) {
            throw new IllegalArgumentException("请设置歌曲列表");
        }
        Songinfo<T> tSonginfo = mSonglist.get(index);
        play(tSonginfo);
    }

    /**
     * 播放
     * @param t
     */
    @Override
    public void play(Songinfo<T> t) {
        // TODO: 18/2/5
        if (t == null) {
            throw new IllegalArgumentException("song cant be null");
        }
        preSong = currentSong;
        currentSong = t;
        curIndex = mSonglist.indexOf(currentSong);
        mPlayer.start(t.getUrl());
    }

    @Override
    public void preSong() {
        Songinfo<T> preSong = getPreSong();
        play(preSong);
    }

    @Override
    public void next() {
        Songinfo<T> nextSong = getNextSong();
        play(nextSong);
    }

    private Songinfo<T> getNextSong() {
        Songinfo<T> nextSong = null;
        int nextIndex = curIndex + 1;
        if (mPlayMode == PlayMode.RANDOM_PLAY) {
            nextIndex = getRandomIndex();
        }
        if (nextIndex == mSonglist.size()) {
            nextIndex = 0;
        }
        nextSong = mSonglist.get(nextIndex);
        return nextSong;
    }

    /**
     * 根据播放模式获取上一曲歌曲
     * @return
     */
    private Songinfo<T> getPreSong() {
        Songinfo<T> preSong = null;
        int preIndex = curIndex - 1;
        if (mPlayMode == PlayMode.RANDOM_PLAY) {
            preIndex = getRandomIndex();
            nextSong = mSonglist.get(preIndex);
        }
        if (preIndex == -1) {
            preIndex = mSonglist.size() - 1;
        }
        preSong= mSonglist.get(preIndex);
        return preSong;
    }

    private int getRandomIndex() {
        int index = new Random().nextInt(mSonglist.size() - 1);
        index = curIndex == index ? ((curIndex == mSonglist.size() - 1) ?
                curIndex -1 : curIndex + 1) : index;
        nextSong = mSonglist.get(index);
        return index;
    }

    @Override
    public void setPlaymode(PlayMode playMode) {
        this.mPlayMode = playMode;
        mPlayer.setLooping(mPlayMode == PlayMode.SINGLE_CYCLE_PLAY);
    }

    @Override
    public void setSongList(List<Songinfo<T>> songList) {
        this.mSonglist = songList;
    }

    @Override
    public void release() {
        mPlayer.release();
        mSonglist = null;
    }

    @Override
    public long getCurrentPosition() {
        return mPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int position) {
        mPlayer.seekTo(position);
    }

    @Override
    public IPlayer getPlayer() {
        return MediaPlayerManager.getInstance();
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }
}
