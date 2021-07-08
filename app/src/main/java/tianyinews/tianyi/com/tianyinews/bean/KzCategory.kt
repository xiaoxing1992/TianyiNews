package com.xk.eyepetizer.mvp.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import tianyinews.tianyi.com.tianyinews.adapter.KyCategoryAdapter
import java.io.Serializable

/**
 * Created by xuekai on 2017/9/3.
 */
data class KzCategory(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val bgPicture: String = "",
    val bgColor: String = "",
    val headerImage: String = ""
) : Serializable, MultiItemEntity {
    override val itemType: Int
        get() = when (checkIsNull()) {
            true -> KyCategoryAdapter.TYPE_END
            else -> KyCategoryAdapter.TYPE_CONTENT
        }

    private fun checkIsNull(): Boolean {
        if (name == "" && description == "" && headerImage == ""&& bgPicture == ""&& bgColor == "") {
            return true
        }
        return false
    }
}
