package tianyinews.tianyi.com.tianyinews.bean

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/7 12:16 下午
 * @Description:
 */
data class VideoResultModel(
    var title: String = "",
    var share_url: String = "",
    var author: String = "",
    var item_cover: String = "",
    var hot_words: String = "",
    var hot_value: Long =  0,
    var play_count: Long =  0,
    var digg_count: Long =  0,
    var comment_count: Long =  0
)