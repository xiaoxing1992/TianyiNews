package tianyinews.tianyi.com.tianyinews.gme

import android.text.TextUtils
import com.tencent.av.sig.AuthBuffer

class GMEAuthBufferHelper private constructor() {
    private var mAppId: String? = null
    private var mKey: String? = null
    private var mOpenId: String? = null
    fun setGEMParams(appId: String?, key: String?, openId: String?) {
        mAppId = appId
        mKey = key
        mOpenId = openId
    }

    fun createAuthBuffer(roomId: String?): ByteArray {
        // Generate AuthBuffer for encryption and authentication of relevant features. For release in the production environment,
        // please use the backend deployment key as detailed in https://intl.cloud.tencent.com/document/product/607/12218
        return if (roomId.isNullOrEmpty()) {
            AuthBuffer.getInstance().genAuthBuffer(mAppId?.toInt()?:0, "0", mOpenId, mKey)
        } else {
            AuthBuffer.getInstance().genAuthBuffer(mAppId?.toInt()?:0, roomId, mOpenId, mKey)
        }
    }

    companion object {
        @Volatile
        private var instance: GMEAuthBufferHelper? = null

        @JvmStatic
        fun getInstance(): GMEAuthBufferHelper {
            return instance ?: synchronized(this) {
                instance ?: GMEAuthBufferHelper().also { instance = it }
            }
        }
    }
}