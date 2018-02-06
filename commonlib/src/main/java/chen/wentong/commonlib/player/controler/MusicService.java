package chen.wentong.commonlib.player.controler;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by wentong.chen on 18/2/5.
 * 功能：
 */

public class MusicService extends Service {

    @Override
    public ComponentName startService(Intent service) {
        return super.startService(service);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
