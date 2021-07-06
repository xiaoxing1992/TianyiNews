package tianyinews.tianyi.com.network.request

import tianyinews.tianyi.com.network.apiService
import tianyinews.tianyi.com.tianyinews.bean.ApiResponse
import tianyinews.tianyi.com.tianyinews.bean.NewsApiResponse
import tianyinews.tianyi.com.tianyinews.bean.NewsModel
import tianyinews.tianyi.com.tianyinews.bean.NewsResultModel

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
        val model = apiService.getNewsList(type, num)
        return model
    }
}