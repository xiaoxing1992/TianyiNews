package tianyinews.tianyi.com.tianyinews.gme

import android.content.Intent

/**
 * Created by baixs on 2017/11/20.
 */
class  TMGCallbackHelper private constructor(){
    private val params2 = Params2()
    fun ParseIntentParams2(intent: Intent): Params2 {
        params2.nErrCode = intent.getIntExtra("result", -1)
        params2.strErrMsg = intent.getStringExtra("error_info")
        return params2
    }

    private val paramsUerInfo = ParamsUerInfo()
    fun ParseUserInfoUpdateInfoIntent(intent: Intent): ParamsUerInfo {
        paramsUerInfo.nEventID = intent.getIntExtra("event_id", 0)
        paramsUerInfo.identifierList = intent.getStringArrayExtra("user_list")
        return paramsUerInfo
    }

    private val paramsAudioDeviceInfo = ParamsAudioDeviceInfo()
    fun ParseAudioDeviceInfoIntent(intent: Intent): ParamsAudioDeviceInfo {
        paramsAudioDeviceInfo.bState = intent.getBooleanExtra("audio_state", false)
        paramsAudioDeviceInfo.nErrCode = intent.getIntExtra("audio_errcode", 0)
        return paramsAudioDeviceInfo
    }

    inner class Params2 {
        var nErrCode = 0
        var strErrMsg: String? = null
    }

    ////
    inner class ParamsUerInfo {
        var nEventID = 0
        var identifierList: Array<String>? = null
    }

    //
    //
    //
    inner class ParamsAudioDeviceInfo {
        var bState = false
        var nErrCode = 0
    }

    companion object {
        @Volatile
        private var mInstance: TMGCallbackHelper? = null

        @JvmStatic
        fun getInstance(): TMGCallbackHelper {
            return mInstance ?: synchronized(this) {
                mInstance ?: TMGCallbackHelper().also { mInstance = it }
            }
        }
    }
}