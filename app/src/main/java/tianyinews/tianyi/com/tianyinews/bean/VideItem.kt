package tianyinews.tianyi.com.tianyinews.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import tianyinews.tianyi.com.tianyinews.adapter.KaiyanRecommendAdapter
import java.io.Serializable

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/7 4:31 下午
 * @Description:
 */
class VideItem(var type: String, val data: Data?, val tag: String) : Serializable, MultiItemEntity {

    data class Data(
        val dataType: String, val text: String, val videoTitle: String, val id: Long, val title: String, val slogan: String?,
        val description: String,
        val actionUrl: String,
        val provider: Provider,
        val category: String,
        val parentReply: ParentReply,
        val author: Author,
        val cover: Cover,
        val likeCount: Int,
        val playUrl: String,
        val thumbPlayUrl: String,
        val duration: Long,
        val message: String,
        val createTime: Long,
        val webUrl: WebUrl,
        val library: String,
        val user: User,
        val playInfo: ArrayList<PlayInfo>,
        val consumption: Consumption,
        val campaign: Any,
        val waterMarks: Any,
        val adTrack: Any,
        val tags: ArrayList<Tag>,
        val type: String,
        val titlePgc: Any,
        val descriptionPgc: Any,
        val remark: String,
        val idx: Int,
        val shareAdTrack: Any,
        val favoriteAdTrack: Any,
        val webAdTrack: Any,
        val date: Long,
        val promotion: Any,
        val label: Any,
        val labelList: Any,
        val descriptionEditor: String,
        val collected: Boolean,
        val played: Boolean,
        val subtitles: Any,
        val lastViewTime: Any,
        val playlists: Any
    ) : Serializable {
        data class Tag(val id: Int, val name: String, val actionUrl: String, val adTrack: Any) : Serializable
        data class Author(val icon: String, val name: String, val description: String) : Serializable

        data class Provider(val name: String, val alias: String, val icon: String) : Serializable

        data class Cover(
            val feed: String, val detail: String,
            val blurred: String, val sharing: String, val homepage: String
        ) : Serializable

        data class WebUrl(val raw: String, val forWeibo: String) : Serializable
        data class PlayInfo(val name: String, val url: String, val type: String, val urlList: ArrayList<Url>) : Serializable
        data class Consumption(val collectionCount: Int, val shareCount: Int, val replyCount: Int) : Serializable
        data class User(val uid: Long, val nickname: String, val avatar: String, val userType: String, val ifPgc: Boolean) : Serializable
        data class ParentReply(val user: User, val message: String) : Serializable
        data class Url(val size: Long) : Serializable

    }

    override val itemType: Int
        get() = when (type) {
            "banner2" -> KaiyanRecommendAdapter.TYPE_BANNER
            "textHeader" -> KaiyanRecommendAdapter.TYPE_HEADER
            "video" -> KaiyanRecommendAdapter.TYPE_VIDEO
            "2002" -> KaiyanRecommendAdapter.TYPE_END
            else -> KaiyanRecommendAdapter.TYPE_OTHER
        }
}