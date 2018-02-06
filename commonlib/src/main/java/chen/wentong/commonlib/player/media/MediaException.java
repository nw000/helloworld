package chen.wentong.commonlib.player.media;

/**
 * Created by wentong.chen on 18/2/5.
 * 功能：音乐错误
 */

public class MediaException extends Exception{
    private static final String TAG = MediaException.class.getSimpleName();

    public MediaException(String charSequence) {
        super(TAG + "音乐错误是：" + charSequence);
    }
}
