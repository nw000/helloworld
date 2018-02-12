package chen.wentong.commonlib.player.media;

/**
 * Created by wentong.chen on 18/2/5.
 * 功能：mediaManager播放状态发出的事件
 */

public enum  MediaEvent {
    INITIALIZED(0, "初始化成功"),
    PREPARE(1, "准备状态"),
    ON_PREPARE(2, "准备完成状态"),
    PLAYING(2, "播放状态"),
    PAUSE(3, "暂停状态"),
    STOP(4, "停止状态"),
    ON_COMPLETE(5, "播放完成状态"),
    ON_ERROR(6, "播放错误状态"),
    SEEK_TO_COMPLITE(7, "拖动播放进度完成"),
    PATH_IS_ILLEGAL(8, "播放路径不合法"),
            ;

    private int status;
    private String desc;
    MediaEvent(int status, String desc) {
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
