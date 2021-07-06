package tianyinews.tianyi.com.tianyinews.bean


/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/5 4:56 下午
 * @Description:
 */
data class NewsResultModel(
    var stat: String = "",
    var page: String = "",
    var pageSize: String = "",
    var data: List<NewsDataModel>? = null
)