package chen.wentong.commonlib.player.media;

/**
 * Created by wentong.chen on 18/2/5.
 * 功能：播放器状态
 */

public enum MediaStatus {

    NO_INITIALIZED(-1, "未初始化"),
    INITIALIZED(0, "初始化成功"),
    PREPARE(1, "准备状态"),
    PLAYING(2, "播放状态"),
    PAUSE(3, "暂停状态"),
    STOP(4, "停止状态"),
    COMPLETE(5, "播放完成状态"),
    ERROR(6, "播放错误状态"),
    PATH_ERROR(7, "音乐播放路径错误")
    ;

    private int status;
    private String desc;
    MediaStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public int getStatus() {
        return status;
    }
}
