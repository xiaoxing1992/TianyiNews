package tianyinews.tianyi.com.tianyinews.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rz.commonlibrary.base.viewmodel.BaseViewModel
import com.rz.commonlibrary.ext.requestT
import tianyinews.tianyi.com.network.request.HomeRequestCoroutine
import tianyinews.tianyi.com.tianyinews.bean.CategoryHotItem
import tianyinews.tianyi.com.tianyinews.bean.ListDataUiState
import tianyinews.tianyi.com.tianyinews.bean.VideItem

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/9 3:52 下午
 * @Description:
 */
class KaiyanHotChildViewModel: BaseViewModel() {

    var liveData: MutableLiveData<ListDataUiState<VideItem>> = MutableLiveData()

    fun reuqestData(actionUrl: String) {
        requestT({
            HomeRequestCoroutine.getIssue(actionUrl)
        },{
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = true,
                    isEmpty = it.itemList?.isEmpty()?:true,
//                    hasMore = it.hasMore(),
                    isFirstEmpty = it.itemList?.isEmpty()?:true,
                    listData = it.itemList
                )
            liveData.value = listDataUiState
        },{
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = true,
                    listData = arrayListOf<VideItem>()
                )
            liveData.value = listDataUiState
        })
    }
}