package chen.wentong.commonlib.player.media;

/**
 * Created by wentong.chen on 18/2/5.
 * 功能：mediaManager播放状态发出的事件
 */

public enum  MediaEvent {
    MEDIA_INITIALIZED(0, "初始化成功"),
    MEDIA_PREPARE(1, "准备状态"),
    MEDIA_START(2, "开始播放状态"),
    MEDIA_PAUSE(3, "暂停状态"),
    MEDIA_STOP(4, "停止状态"),
    MEDIA_COMPLETE(5, "播放完成状态"),
    MEDIA_ERROR(6, "播放错误状态"),
    MEDIA_SEEK_TO_COMPLITE(7, "拖动播放进度完成"),
    MEDIA_PATH_IS_ERROR(8, "播放路径错误"),
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
