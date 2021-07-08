package tianyinews.tianyi.com.network.api

import com.xk.eyepetizer.mvp.model.bean.KzCategory
import retrofit2.http.*
import tianyinews.tianyi.com.tianyinews.bean.*

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/5 3:44 下午
 * @Description:
 */
interface HomeService {
    companion object {
        const val NEWS_URL = "http://v.juhe.cn/"
        const val VIDEO_URL = "http://baobab.kaiyanapp.com/api/"
    }

    @FormUrlEncoded
    @POST("toutiao/index")
    suspend fun getNewsList(@Field("type") type: String,
                            @Field("page") page: Int,
                            @Field("key") key: String = "ac9f896cc6df7e0e1381790f7d2da8e7")
    : NewsApiResponse<NewsResultModel>

//    @FormUrlEncoded
//    @POST("fapig/douyin/billboard")
    @GET("v2/feed?&num=1")
    suspend fun getVideoData(@Query("date") date: Long)
            : VideoApiResponse<List<VideoIssue>>

    /**
     * 根据nextpageurl请求数据
     */
    @GET
    suspend fun getVideoMoreData(@Url url: String): VideoApiResponse<List<VideoIssue>>

    /**
     * 获取分类
     */
    @GET("v4/categories")
    suspend  fun getCategory(): List<KzCategory>
}