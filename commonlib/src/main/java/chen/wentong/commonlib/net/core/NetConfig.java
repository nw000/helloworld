package chen.wentong.commonlib.net.core;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Interceptor;

/**
 * Created by wentong.chen on 18/1/26.
 * 功能：
 */

public class NetConfig {

    private long mConnectTimeout = 30 * 1000;                                        //连接超时
    private long mReadTimeout;
    private SSLSocketFactory mSSLSocketFactory;                          //验证书
    private boolean mToggleHttps;                                       //是否https验证
    private boolean mLogEnable;                                         //是否开启请求日志
    private CopyOnWriteArrayList<Interceptor> mInterceptors;            //请求拦截器

    public NetConfig() {

    }

    public void setConnectTimeout(long connectTimeout) {
        mConnectTimeout = connectTimeout;
    }

    public long getConnectTimeout() {
        return mConnectTimeout;
    }

    public long getReadTimeout() {
        return mReadTimeout;
    }

    public void setReadTimeout(long readTimeout) {
        mReadTimeout = readTimeout;
    }

    public SSLSocketFactory getSSLSocketFactory() {
        return mSSLSocketFactory;
    }

    public boolean isToggleHttps() {
        return mToggleHttps;
    }

    public void setToggleHttps(boolean toggleHttps) {
        this.mToggleHttps = toggleHttps;
    }

    public void setSSLSocketFactory(SSLSocketFactory SSLSocketFactory) {
        mSSLSocketFactory = SSLSocketFactory;
    }

    public boolean isLogEnable() {
        return mLogEnable;
    }

    public void setLogEnable(boolean logEnable) {
        mLogEnable = logEnable;
    }

    public List<Interceptor> getInterceptors() {
        return mInterceptors;
    }

    public void addInterceptor(Interceptor interceptor) {
        if (mInterceptors == null) {
            mInterceptors = new CopyOnWriteArrayList<>();
        }
        if (interceptor != null && !mInterceptors.contains(interceptor)) {
            mInterceptors.add(interceptor);
        }
    }

    public static class Builder {

        private final NetConfig mNetConfig;
        private long mConnectTimeout;

        public Builder() {
            mNetConfig = new NetConfig();
        }

        public Builder setConnectTimeout(long connectTimeout) {
            mConnectTimeout = connectTimeout;
            return this;
        }

        public NetConfig build() {
            return mNetConfig;
        }
    }
}
