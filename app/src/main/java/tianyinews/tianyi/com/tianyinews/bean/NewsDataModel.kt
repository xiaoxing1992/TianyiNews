package tianyinews.tianyi.com.tianyinews.bean

import android.text.TextUtils
import com.chad.library.adapter.base.entity.MultiItemEntity
import retrofit2.http.Field
import java.util.*

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/5 4:57 下午
 * @Description:
 */
data class NewsDataModel(
    var uniquekey: String = "",
    var title: String = "",
    var date: String = "",
    var category: String = "",
    var author_name: String = "",
    var url: String = "",
    var thumbnail_pic_s: String = "",
    var thumbnail_pic_s02: String = "",
    var thumbnail_pic_s03: String = "",
    var is_content: String = "",
) : MultiItemEntity {

    private fun getImagesSize(): Int {
        val urls: MutableList<String> = ArrayList()
        if (!TextUtils.isEmpty(thumbnail_pic_s)) {
            urls.add(thumbnail_pic_s)
        }
        if (!TextUtils.isEmpty(thumbnail_pic_s02)) {
            urls.add(thumbnail_pic_s02)
        }
        if (!TextUtils.isEmpty(thumbnail_pic_s03)) {
            urls.add(thumbnail_pic_s03)
        }
        return urls.size
    }

    override val itemType: Int
        get() = when (getImagesSize()) {
            0 -> NewsMultiItemEntity.TEXT
            1 -> NewsMultiItemEntity.IMG
            2 -> NewsMultiItemEntity.IMG_TWO
            else -> NewsMultiItemEntity.IMG_THREE
        }

}