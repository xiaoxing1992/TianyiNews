package tianyinews.tianyi.com.tianyinews.ext

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import tianyinews.tianyi.com.tianyinews.R

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/7 6:27 下午
 * @Description:
 */
object ImageViewAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageUrl(imageView: ImageView, url: String) {
        imageView.load(url) {
            placeholder(R.drawable.image_placeholder)
        }
    }


    @BindingAdapter(value = ["circleUrl", "isCircle"], requireAll = false)
    @JvmStatic
    fun setCircleUrl(imageView: ImageView, url: String, isCircle: Boolean = false) {
        imageView.load(url) {
//            placeholder(holderDrawable)
//            error(errorDrawable)
            if (isCircle) {
                transformations(CircleCropTransformation())
            }
        }
    }
}