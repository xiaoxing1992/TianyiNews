package tianyinews.tianyi.com.network.request

import com.xk.eyepetizer.mvp.model.bean.KzCategory
import tianyinews.tianyi.com.network.api.HomeService
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

    suspend fun getCategory(): List<KzCategory> {
        return apiVideoService.getCategory()
    }

    suspend fun getHotCategoryData(): KzCategoryHot {
        return apiVideoService.getHotCategoryData()
    }

    suspend fun getIssue(actionUrl: String): VideoIssue {
        val removePrefix = actionUrl.removePrefix(HomeService.VIDEO_URL)
        return apiVideoService.getIssue(actionUrl)
    }
}