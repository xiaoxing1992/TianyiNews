package tianyinews.tianyi.com.tianyinews.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rz.commonlibrary.base.viewmodel.BaseViewModel
import com.rz.commonlibrary.ext.requestT
import com.xk.eyepetizer.mvp.model.bean.KzCategory
import tianyinews.tianyi.com.network.request.HomeRequestCoroutine
import tianyinews.tianyi.com.tianyinews.bean.ListDataUiState

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/8 5:19 下午
 * @Description:
 */
class KzCategoryViewModel : BaseViewModel() {
    var liveData: MutableLiveData<ListDataUiState<KzCategory>> = MutableLiveData()

    fun requestData() {
        requestT({
            HomeRequestCoroutine.getCategory()
        }, {
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = true,
                    isEmpty = it.isEmpty(),
//                    hasMore = it.hasMore(),
                    isFirstEmpty = it.isEmpty(),
                    listData = it
                )
            liveData.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = true,
                    listData = arrayListOf<KzCategory>()
                )
            liveData.value = listDataUiState
        })
    }


}