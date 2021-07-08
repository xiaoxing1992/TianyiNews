package tianyinews.tianyi.com.network.request

import tianyinews.tianyi.com.network.apiService
import tianyinews.tianyi.com.network.apiVideoService
import tianyinews.tianyi.com.tianyinews.bean.*

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/5 4:02 下午
 * @Description:
 */
val HomeRequestCoroutine: HomeReuqestManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    HomeReuqestManager()
}

class HomeReuqestManager {
    suspend fun getNewsList(type: String, num: Int): NewsApiResponse<NewsResultModel> {
        return apiService.getNewsList(type, num)
    }

//    suspend fun getVideoList(type: String, num: Int): NewsApiResponse<List<VideoResultModel>> {
////        return apiVideoService.getVideoList(type,num)
//    }

    suspend fun getVideoData(): VideoApiResponse<List<VideoIssue>> {
        return apiVideoService.getVideoData(System.currentTimeMillis())
    }

    suspend fun getVideoMoreData(nextUrl: String): VideoApiResponse<List<VideoIssue>> {
        return apiVideoService.getVideoMoreData(nextUrl)
    }
}