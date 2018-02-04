package com.example.nw.helloworld;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by wentong.chen on 18/1/31.
 * 功能：
 */

public interface NetService {
    String HEADER_API_VERSION = "Accept: application/vnd.github.v3+json";  //添加请求头
    @Headers({HEADER_API_VERSION})
    @GET("/users")
    Observable<List<UserInfo>> getUserInfos(@Query("since") int lastIdQueried, @Query("per_page") int perPage);

    @Headers({HEADER_API_VERSION})
    @GET("/users/{username}")
    Observable<UserInfo> getUserInfo(@Path("username") String username);
}
