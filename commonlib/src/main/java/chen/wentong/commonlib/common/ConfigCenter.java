package chen.wentong.commonlib.common;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import chen.wentong.commonlib.BuildConfig;


/**
 * 获取配置文件中的baseUrl数据
 * Created by wentong.chen on 18/1/22.
 */
public class ConfigCenter {
    private static final String ASSETS_FILE_NAME = "config_center.json";
    public static final String DEFAULT_KEY = "default";
    private JSONObject dataSource;

    private static final class ConfigCenterHolder {
        private static final ConfigCenter INSTANCE = new ConfigCenter();
    }

    public static ConfigCenter getInstance() {
        return ConfigCenterHolder.INSTANCE;
    }

    /**
     * 初始化配置文件的读写流
     * @param context
     */
    private synchronized void ensureInit(Context context) {
        if (dataSource == null) {
            InputStream is = null;
            ByteArrayOutputStream fos = null;
            try {
                is = context.getAssets().open(ASSETS_FILE_NAME);
                int len = -1;
                byte[] buffer = new byte[1024];

                fos = new ByteArrayOutputStream();
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                String content = new String(fos.toByteArray());
                dataSource = new JSONObject(content);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    is.close();
                    fos.close();
                } catch (Throwable e) {

                }
            }
        }
    }

    /**
     * 根据key获取配置文件的value（如："default": "https://api.github.com"）
     * @param context
     * @param name key
     * @return  value
     */
    public Object get(Context context, String name) {
        ensureInit(context);
        try {
            JSONObject ds = dataSource;
            Object val = ds.get(name);
            if (!(val instanceof JSONObject)) {
                if (val instanceof JSONArray) {
                    throw new RuntimeException("Not support array!!!");
                }
                return val;
            }
            JSONObject jval = (JSONObject) val;
            String buildType = BuildConfig.BUILD_TYPE.toLowerCase();
            if (jval.has(buildType)) {
                return jval.get(buildType);
            } else {
                if (!jval.has(DEFAULT_KEY)) {
                    throw new RuntimeException("Can not find key '" + buildType + "' and '" + DEFAULT_KEY + "' !!");
                }

                return jval.get(DEFAULT_KEY);

            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String getString(Context context, String name) {
        Object result = get(context, name);
        return result.toString();
    }

    public boolean getBoolean(Context context, String name) {
        Object result = get(context, name);
        return Boolean.valueOf(result.toString());
    }

    public double getDouble(Context context, String name) {
        Object result = get(context, name);
        return Double.valueOf(result.toString());
    }

    public int getInt(Context context, String name) {
        Object result = get(context, name);
        return Integer.valueOf(result.toString());
    }

    public long getLong(Context context, String name) {
        Object result = get(context, name);
        return Long.valueOf(result.toString());
    }
}
