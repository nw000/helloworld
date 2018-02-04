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
    private static String sBaseUrl;
    private static StringBuffer sb = new StringBuffer();
    private Class mNetService;
//
    private Map<String, NetConfig> mNetConfigMap = new HashMap<>();
    private Map<String, Object> mCacheServiceMap = new HashMap<>();
    private Map<String, Object> mNetServiceMap = new HashMap<>();

    public static final long connectTimeoutMills = 10 * 1000l;
    public static final long readTimeoutMills = 10 * 1000l;

    private static RequestManager instance;
    private File cacheFile;
    private Class cacheService;

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

    public static <S> S getNet() {
        return (S) getNet(getInstance().mNetService);
    }

    /**
     * 获取http请求api
     * @param <S>
     * @return
     */
    public static  <S> S getNet(Class<S> service) {
        return getInstance().getNet(sBaseUrl, service);
    }

    //获取指定的请求api
    public static  <S> S getNet(String baseUrl, Class<S> service) {
        NetConfig netConfig = getInstance().getNetConfig(baseUrl);
        getInstance().checkBaseUrl(baseUrl);
        sb.delete(0, sb.length());
        String key = sb.append(baseUrl).append(service.getCanonicalName())
                .append(netConfig.getSSLSocketFactory() == null ? "" :
                        netConfig.getSSLSocketFactory().getClass()).toString();
        if (getInstance().mNetServiceMap.get(key) != null) {
            return (S) getInstance().mNetServiceMap.get(key);
        }
        S s = getInstance().getRetrofit(baseUrl).create(service);
        S netService = (S) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service},
                new NetInvocationHandler<>(s));
        getInstance().mNetServiceMap.put(key, netService);
        return netService;
    }

    public static <S> S getCacheNet() {
        return (S) getCacheNet(getInstance().cacheFile, getInstance().cacheService);
    }

    /**
     * 获取网络缓存的api
     * @param cacheFile 缓存存放位置
     * @param cacheService 缓存api
     * @param <S>
     * @return
     */
    public static  <S> S getCacheNet(File cacheFile, Class<S> cacheService) {
        if (cacheFile == null) {
            throw new IllegalArgumentException("file cant be null or arg is not file");
        }
        sb.delete(0, sb.length());
        String key = sb.append(cacheFile.getAbsolutePath() + cacheService.getCanonicalName()).toString();
        if (getInstance().mCacheServiceMap.get(key) != null) {
            return (S) getInstance().mCacheServiceMap.get(key);
        }
        S s = new RxCache.Builder()
                .persistence(cacheFile, new GsonSpeaker())
                .using(cacheService);
        //添加请求数据时指定io线程的代理操作
        S cacheProvider = (S) Proxy.newProxyInstance(cacheService.getClassLoader(),
                new Class[]{cacheService}, new NetInvocationHandler<>(s));
        getInstance().mCacheServiceMap.put(key, cacheFile);
        return cacheProvider;
    }

    private void checkBaseUrl(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new IllegalStateException("must setBaseusl first");
        }
    }

    public void initBaseUrl(String baseUrl, Class netService) {
        initBaseUrl(baseUrl, netService,null);
    }

    public void initBaseUrl(String baseUrl, Class netService, NetConfig netConfig) {
        this.sBaseUrl = baseUrl;
        this.mNetService = netService;
        registerNetConfig(baseUrl, netConfig);

    }

    public void initCache(File file, Class cacheService) {
        this.cacheFile = file;
        this.cacheService = cacheService;
    }

    public void registerNetConfig(String baseUrl, NetConfig netConfig) {
        getInstance().checkBaseUrl(baseUrl);
        if (netConfig == null) {
            netConfig = getDefaultNetConfig();
        }
        getInstance().mNetConfigMap.remove(baseUrl);
        getInstance().mNetConfigMap.put(baseUrl, netConfig);
        getInstance().mNetServiceMap.remove(baseUrl);
    }


    private Retrofit getRetrofit(String baseUrl) {
        checkBaseUrl(baseUrl);
        NetConfig netConfig = getNetConfig(baseUrl);
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

    public NetConfig getDefaultNetConfig() {
        return new NetConfig();
    }

    public NetConfig getNetConfig(String baseUrl) {
        if (mNetConfigMap.get(baseUrl) != null) {
            return mNetConfigMap.get(baseUrl);
        }
        NetConfig defaultNetConfig = getDefaultNetConfig();
        mNetConfigMap.put(baseUrl, defaultNetConfig);
        return defaultNetConfig;
    }

    public static void clearCache() {
        getInstance().mCacheServiceMap.clear();
        getInstance().mNetServiceMap.clear();
        getInstance().mNetConfigMap.clear();
    }

    public void removeNetService(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            getInstance().mNetServiceMap.remove(baseUrl);
        }
    }
}
