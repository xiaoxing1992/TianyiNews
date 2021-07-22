package tianyinews.tianyi.com.tianyinews.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ImmersionBar
import com.rz.commonlibrary.base.appContext
import com.tencent.TMG.ITMGContext
import com.tencent.av.sdk.AVError
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.base.BaseActivity
import tianyinews.tianyi.com.tianyinews.databinding.ActivityRidevoiceCreateBinding
import tianyinews.tianyi.com.tianyinews.delegate.GMEDelegate
import tianyinews.tianyi.com.tianyinews.gme.GMEInitListener
import tianyinews.tianyi.com.tianyinews.gme.TMGCallbackDispatcher
import tianyinews.tianyi.com.tianyinews.gme.TMGCallbackHelper
import tianyinews.tianyi.com.tianyinews.gme.TMGDispatcherBase
import tianyinews.tianyi.com.tianyinews.viewmodel.RideVoiceCreateViewModel
import java.util.concurrent.ScheduledThreadPoolExecutor

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/13 11:33 上午
 * @Description:
 */
class VoiceCreateActivity : BaseActivity<RideVoiceCreateViewModel, ActivityRidevoiceCreateBinding>(), TMGDispatcherBase {

    override fun layoutId(): Int = R.layout.activity_ridevoice_create

    override fun initView(savedInstanceState: Bundle?) {
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).init()
        mDatabind.tvCreate.isEnabled = false
        KeyboardUtils.showSoftInput(mDatabind.etCode)
        TMGCallbackDispatcher.getInstance().registerSubDispatcher(this)
    }

    override fun createObserver() {
        super.createObserver()
    }

    override fun initListener() {
        mDatabind.ivBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.tvJoin.setOnClickListener {
            if (mDatabind.etCode.text.isNullOrEmpty()) {
                ToastUtils.showShort("Please Input Code 6-8 Bit!!")
                return@setOnClickListener
            }
            if (mDatabind.etCode.text.length < 6 || mDatabind.etCode.text.length > 8) {
                ToastUtils.showShort("Please Input Code 6-8 Bit!!")
                return@setOnClickListener
            }

            val roomId = mDatabind.etCode.text.trim()
            GMEDelegate.getInstance().init(this, object : GMEInitListener {
                override fun isInit(isinit: Boolean) {
                    when (isinit) {
                        true -> {
                            GMEDelegate.getInstance().enterGMERoom(appContext, roomId.toString())
                        }
                        else -> mDatabind.tvCreate.isEnabled = false
                    }
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        TMGCallbackDispatcher.getInstance().unregisterSubDispatcher(this)
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, VoiceCreateActivity::class.java)
//                .putExtra()
            context.startActivity(starter)
        }
    }

    override fun OnEvent(type: ITMGContext.ITMG_MAIN_EVENT_TYPE?, data: Intent?) {
        if (data == null) return
        when (type) {
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_NONE -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_ENTER_ROOM -> {
                var nErrCode = TMGCallbackHelper.getInstance().ParseIntentParams2(data).nErrCode
                var strMsg = TMGCallbackHelper.getInstance().ParseIntentParams2(data).strErrMsg
                when (nErrCode) {
                    AVError.AV_OK -> {
                        GMEDelegate.getInstance().enableMic(true)
                        GMEDelegate.getInstance().enableSpeaker(true)
                        VoiceDetailActivity.start(this@VoiceCreateActivity)
                    }
                    else -> GMEDelegate.getInstance().exitGMERoom(appContext)
                }
            }
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_EXIT_ROOM -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_ROOM_DISCONNECT -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVNET_TYPE_USER_UPDATE -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_NUMBER_OF_USERS_UPDATE -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_NUMBER_OF_AUDIOSTREAMS_UPDATE -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_RECONNECT_START -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_RECONNECT_SUCCESS -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_SWITCH_ROOM -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_CHANGE_ROOM_TYPE -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_AUDIO_DATA_EMPTY ->{}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_ROOM_SHARING_START -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_ROOM_SHARING_STOP -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_RECORD_COMPLETED -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_RECORD_PREVIEW_COMPLETED -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_RECORD_MIX_COMPLETED -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVNET_TYPE_USER_VOLUMES -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_ACCOMPANY_FINISH -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_SERVER_AUDIO_ROUTE_EVENT ->{}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_CUSTOMDATA_UPDATE -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_REALTIME_ASR -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_CHORUS_EVENT -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_CHANGETEAMID ->{}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_HARDWARE_TEST_RECORD_FINISH -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_HARDWARE_TEST_PREVIEW_FINISH -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVNET_TYPE_PTT_RECORD_COMPLETE -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVNET_TYPE_PTT_UPLOAD_COMPLETE ->{}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVNET_TYPE_PTT_DOWNLOAD_COMPLETE -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVNET_TYPE_PTT_PLAY_COMPLETE ->{}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVNET_TYPE_PTT_SPEECH2TEXT_COMPLETE -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVNET_TYPE_PTT_STREAMINGRECOGNITION_COMPLETE -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVNET_TYPE_PTT_STREAMINGRECOGNITION_IS_RUNNING -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVNET_TYPE_ROOM_MANAGEMENT_OPERATOR -> {}
            ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_CHANGE_ROOM_QUALITY -> {}
            null -> {
            }
        }
    }
}