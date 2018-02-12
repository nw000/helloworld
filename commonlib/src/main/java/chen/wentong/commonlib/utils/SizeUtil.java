package chen.wentong.commonlib.utils;

import android.content.Context;

/**
 * Created by wentong.chen on 18/2/4.
 * 功能：
 */

public class SizeUtil {

    private static Context sContext;

    /**
     *
     * @param context 上下文请使用application
     */
    public static void init(Context context) {
        sContext = context;
    }

    public static int dp2px(int dpVal) {
        float density = sContext.getResources().getDisplayMetrics().density;
        return (int) (dpVal * density + 0.5f);
    }

    /**
     * sp转px
     * @param spVal 字体大小
     * @return px大小
     */
    public static int sp2px(int spVal) {
        float scaledDensity = sContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (scaledDensity * spVal + 0.5f);
    }
}
