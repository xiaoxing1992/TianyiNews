package tianyinews.tianyi.com.tianyinews.bean

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/7 4:30 下午
 * @Description:
 */
data class VideoIssue(
    val releaseTime: Long = 0,
    val type: String = "",
    val date: Long = 0,
    val total: Int = 0,
    val publishTime: Long,
    var itemList:MutableList<VideItem>,
    var count: Int = 0,
    val nextPageUrl: String = ""
)