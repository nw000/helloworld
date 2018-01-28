package chen.wentong.commonlib.net.core;

import android.text.TextUtils;


import java.io.File;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wanglei on 2016/12/24.
 */

public class RequestManager {
//    //网络配置
    private static final String HTTP_REQUEST = "HTTP_REQUEST";
    private static final String HTTPS_REQUEST = "HTTPS_REQUEST";
    private static NetConfig sNetConfig = null;
    private static String sBaseUrl;
    private StringBuffer sb = new StringBuffer();
//
    private Map<String, NetConfig> mNetConfigMap = new HashMap<>();
    private Map<String, Object> mCacheServices = new HashMap<>();
    private Map<String, Object> mNetService = new HashMap<>();

    public static final long connectTimeoutMills = 10 * 1000l;
    public static final long readTimeoutMills = 10 * 1000l;

    private static RequestManager instance;

    private RequestManager() {

    }

    public static RequestManager getInstance() {
        if (instance == null) {
            synchronized (RequestManager.class) {
                if (instance == null) {
                    instance = new RequestManager();
                }
            }
        }
        return instance;
    }

    /**
     * 获取http请求api
     * @param <S>
     * @return
     */
    public <S> S getNet(Class<S> service) {
        return getNet(sBaseUrl, service);
    }

    //获取指定的请求api
    public <S> S getNet(String baseUrl, Class<S> service) {

        NetConfig netConfig = getNetConfig(baseUrl);
        checkNetconfig(netConfig);
        checkBaseUrl(baseUrl);
        sb.delete(0, sb.length());
        String key = sb.append(baseUrl).append(service.getCanonicalName())
                .append(netConfig.getSSLSocketFactory() == null ? "" :
                        netConfig.getSSLSocketFactory().getClass()).toString();
        if (mNetService.get(key) != null) {
            return (S) mNetService.get(key);
        }
        S s = getInstance().getRetrofit(baseUrl).create(service);
        S netService = (S) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service},
                new NetInvocationHandler<>(s));
        mNetService.put(key, netService);
        return netService;
    }

    public NetConfig getNetConfig(String baseUrl) {
        if (mNetConfigMap.get(baseUrl) != null) {
            return mNetConfigMap.get(baseUrl);
        }
        return sNetConfig;
    }

    /**
     * 获取网络缓存的api
     * @param cacheFile 缓存存放位置
     * @param cacheService 缓存api
     * @param <S>
     * @return
     */
    public <S> S getCacheNet(File cacheFile, Class<S> cacheService) {
        if (cacheFile == null) {
            throw new IllegalArgumentException("file cant be null or arg is not file");
        }
        sb.delete(0, sb.length());
        String key = sb.append(cacheFile.getAbsolutePath() + cacheService.getCanonicalName()).toString();
        if (mCacheServices.get(key) != null) {
            return (S) mCacheServices.get(key);
        }
        S s = new RxCache.Builder()
                .persistence(cacheFile, new GsonSpeaker())
                .using(cacheService);
        //添加请求数据时指定io线程的代理操作
        S cacheProvider = (S) Proxy.newProxyInstance(cacheService.getClassLoader(),
                new Class[]{cacheService}, new NetInvocationHandler<>(s));
        mCacheServices.put(key, cacheFile);
        return cacheProvider;
    }

    private void checkNetconfig(NetConfig netConfig) {
        if (netConfig == null) {
            throw new IllegalStateException("must set netConfig first");
        }
    }

    private void checkBaseUrl(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new IllegalStateException("must setBaseusl first");
        }
    }


    public static void setCommonConfig(String baseUrl, NetConfig netConfig) {
        sBaseUrl = baseUrl;
        RequestManager.sNetConfig = netConfig;
    }

    public static void registerNetConfig(String baseUrl, NetConfig netConfig) {
        getInstance().checkNetconfig(netConfig);
    }


    public Retrofit getRetrofit(String baseUrl) {
        return getRetrofit(baseUrl, null);
    }


    public Retrofit getRetrofit(String baseUrl, NetConfig netConfig) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new IllegalStateException("baseUrl can not be null");
        }

        if (netConfig == null) {
            netConfig = mNetConfigMap.get(baseUrl);
            if (netConfig == null) {
                netConfig = sNetConfig;
            }

        }
        checkNetconfig(netConfig);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getClient(baseUrl, netConfig))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        mNetConfigMap.put(baseUrl, netConfig);
        return retrofit;
    }

    private OkHttpClient getClient(String baseUrl, NetConfig netConfig) {

        checkNetconfig(netConfig);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(netConfig.getConnectTimeout() != 0
                ? netConfig.getConnectTimeout()
                : connectTimeoutMills, TimeUnit.MILLISECONDS);
        builder.readTimeout(netConfig.getReadTimeout() != 0
                ? netConfig.getReadTimeout() : readTimeoutMills, TimeUnit.MILLISECONDS);
        //cookie 设置
//        CookieJar cookieJar = provider.configCookie();
//        if (cookieJar != null) {
//            builder.cookieJar(cookieJar);
//        }

        //配置https或者http请求
        if (netConfig.getSSLSocketFactory() != null && netConfig.isToggleHttps()) {
            builder.sslSocketFactory(netConfig.getSSLSocketFactory());
        }

        if (netConfig.getInterceptors() != null) {
            List<Interceptor> interceptors = netConfig.getInterceptors();
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        if (netConfig.isLogEnable()) {
            builder.addInterceptor(new HttpLoggingInterceptor());
        }

        OkHttpClient client = builder.build();
        mNetConfigMap.put(baseUrl, netConfig);

        return client;
    }

    public static NetConfig getCommonNetConfig() {
        return sNetConfig;
    }



    public static void clearCache() {
        getInstance().mCacheServices.clear();
        getInstance().mNetService.clear();
        getInstance().mNetConfigMap.clear();
    }
}
