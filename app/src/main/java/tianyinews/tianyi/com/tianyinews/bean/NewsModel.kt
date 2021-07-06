package tianyinews.tianyi.com.tianyinews.bean


/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/5 4:55 下午
 * @Description:
 */
data class NewsModel(
    var reason: String = "",
    var result: NewsResultModel? = null,
    var error_code :Int= 0
)