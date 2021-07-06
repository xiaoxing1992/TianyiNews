package tianyinews.tianyi.com.network.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import tianyinews.tianyi.com.tianyinews.bean.ApiResponse
import tianyinews.tianyi.com.tianyinews.bean.NewsApiResponse
import tianyinews.tianyi.com.tianyinews.bean.NewsModel
import tianyinews.tianyi.com.tianyinews.bean.NewsResultModel

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/5 3:44 下午
 * @Description:
 */
interface HomeService {
    companion object {
        const val NEWS_URL = "http://v.juhe.cn/"
    }

    @FormUrlEncoded
    @POST("toutiao/index")
    suspend fun getNewsList(@Field("type") type: String,
                            @Field("page") page: Int,
                            @Field("key") key: String = "ac9f896cc6df7e0e1381790f7d2da8e7")
    : NewsApiResponse<NewsResultModel>
}