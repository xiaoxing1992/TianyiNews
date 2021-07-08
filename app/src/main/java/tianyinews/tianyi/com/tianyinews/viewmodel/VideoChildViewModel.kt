package tianyinews.tianyi.com.tianyinews.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rz.commonlibrary.base.viewmodel.BaseViewModel
import com.rz.commonlibrary.ext.request
import tianyinews.tianyi.com.network.request.HomeRequestCoroutine
import tianyinews.tianyi.com.tianyinews.bean.ListDataUiState
import tianyinews.tianyi.com.tianyinews.bean.VideoResultModel

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/7 11:57 上午
 * @Description:
 */
class VideoChildViewModel: BaseViewModel() {

    //页码 首页数据页码从0开始
    var pageNo = 1
    //视频列表数据
    var homeDataState: MutableLiveData<ListDataUiState<VideoResultModel>> = MutableLiveData()
    fun reuqestData(isRefresh: Boolean, type: String) {
        if (isRefresh) {
            pageNo = 1
        }

//        request({
//            HomeRequestCoroutine.getVideoList(type,pageNo)
//        },{
//            //请求成功
//            pageNo++
//            val listDataUiState =
//                ListDataUiState(
//                    isSuccess = true,
//                    isRefresh = isRefresh,
//                    isEmpty = it.isEmpty(),
////                    hasMore = it.hasMore(),
//                    isFirstEmpty = isRefresh && it?.isEmpty() ?: true,
//                    listData = it
//                )
//            homeDataState.value = listDataUiState
//        },{
//            //请求失败
//            val listDataUiState =
//                ListDataUiState(
//                    isSuccess = false,
//                    errMessage = it.errorMsg,
//                    isRefresh = isRefresh,
//                    listData = arrayListOf<VideoResultModel>()
//                )
//            homeDataState.value = listDataUiState
//        })
    }
}