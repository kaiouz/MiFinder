package top.codemaster.mifinder.service;


import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import top.codemaster.mifinder.data.Resource;

/**
 * 资源服务
 */
public interface ResourceService {

    /**
     * 获取指定类型资源
     *
     * @param type 资源类型
     * @return
     */
    @GET("/resources")
    Single<List<Resource>> getResources(@Query("type") Resource.ResType type);


    /**
     * 获取所有类型资源
     *
     * @return
     */
    @GET("/resources")
    Single<List<Resource>> getResources();
}
