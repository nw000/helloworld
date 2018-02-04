package chen.wentong.commonlib.utils.imageutil.config;

/**
 * Created by wentong.chen on 2018/2/2.
 * 图片加载形状定义
 */
public interface ShapeMode {
    /**
     * 直角矩形
     */
    int RECT = 0;

    /**
     * 圆角矩形
     */
    int RECT_ROUND = 1;

    /**
     * 椭圆/圆
     */
    int OVAL = 2;

    /**
     *正方形
     */
    int SQUARE = 3;

}