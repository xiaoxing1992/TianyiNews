package tianyinews.tianyi.com.tianyinews.delegate

import android.content.Context
import com.blankj.utilcode.util.ToastUtils
import com.rz.commonlibrary.base.appContext
import com.tencent.TMG.ITMGContext
import com.tencent.av.sdk.AVError
import tianyinews.tianyi.com.tianyinews.constants.RideVoiceConstants
import tianyinews.tianyi.com.tianyinews.gme.EnginePollHelper
import tianyinews.tianyi.com.tianyinews.gme.GMEAuthBufferHelper
import tianyinews.tianyi.com.tianyinews.gme.GMEInitListener
import tianyinews.tianyi.com.tianyinews.gme.TMGCallbackDispatcher

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/13 12:25 下午
 * @Description:
 */
class GMEDelegate private constructor(){

    private var memberId: String = ""

    init {
        memberId = "${System.currentTimeMillis() % 1000000}"
    }

    fun init(contet: Context,listener: GMEInitListener?=null) {
        val tmgContext = ITMGContext.GetInstance(contet)
        tmgContext.SetTMGDelegate(TMGCallbackDispatcher.getInstance())
        val nRet = tmgContext.Init(RideVoiceConstants.GMEAppID, memberId)
        when(nRet){
            AVError.AV_OK->{
                GMEAuthBufferHelper.getInstance().setGEMParams(RideVoiceConstants.GMEAppID,RideVoiceConstants.GMEAuthKey,memberId)
                EnginePollHelper.createEnginePollHelper()
                ToastUtils.showShort("Init success")
                listener?.isInit(true)
            }
            AVError.AV_ERR_HAS_IN_THE_STATE->{ // 已经初始化过了,可以认为本次操作是成功的
                ToastUtils.showShort("Init success")
                listener?.isInit(true)
            }
            else ->{
                ToastUtils.showShort("Init error errorCode:$nRet")
                listener?.isInit(false)
            }
        }
    }

    fun enterGMERoom(contet: Context,roomId:String){
        val authBuffer = GMEAuthBufferHelper.getInstance().createAuthBuffer(roomId)


        // Step 5/11 enter room, then you need handle ITMG_MAIN_EVENT_TYPE_ENTER_ROOM,ITMG_MAIN_EVNET_TYPE_USER_UPDATE
        // and ITMG_MAIN_EVENT_TYPE_ROOM_DISCONNECT event
        // https://cloud.tencent.com/document/product/607/15210#.3Ca-id.3D.22enterroom.22.3E.E5.8A.A0.E5.85.A5.E6.88.BF.E9.97.B4.3C.2Fa.3E
        ITMGContext.GetInstance(contet).EnterRoom(roomId, ITMGContext.ITMG_ROOM_TYPE_FLUENCY, authBuffer)
    }

    fun exitGMERoom(contet: Context){
        // Step 10/11 exit room
        // 这里需要处理 ITMG_MAIN_EVENT_TYPE_EXIT_ROOM 事件
        // https://cloud.tencent.com/document/product/607/15210#.3Ca-id.3D.22exitroom.22.3E.E9.80.80.E5.87.BA.E6.88.BF.E9.97.B4.3C.2Fa.3E
        ITMGContext.GetInstance(contet).ExitRoom()
    }

    fun enableMic(isMic:Boolean){
        val gmeContext = ITMGContext.GetInstance(appContext)
        gmeContext.GetAudioCtrl().EnableMic(isMic)
    }

     fun enableSpeaker(isSpeaker:Boolean){
         val gmeContext = ITMGContext.GetInstance(appContext)
         gmeContext.GetAudioCtrl().EnableSpeaker(isSpeaker)
    }

    fun unInit(contet: Context){
        // 反初始化
        // https://cloud.tencent.com/document/product/607/15210#.3Ca-id.3D.22uninit.22.3E.E5.8F.8D.E5.88.9D.E5.A7.8B.E5.8C.96-sdk.3C.2Fa.3E
        ITMGContext.GetInstance(contet).Uninit()
    }




    companion object {
        @Volatile
        private var instance: GMEDelegate? = null

        @JvmStatic
        fun getInstance(): GMEDelegate {
            return instance ?: synchronized(this) {
                instance ?: GMEDelegate().also { instance = it }
            }
        }
    }
}