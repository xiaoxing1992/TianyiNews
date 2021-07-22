package tianyinews.tianyi.com.tianyinews.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rz.commonlibrary.base.viewmodel.BaseViewModel
import com.rz.commonlibrary.ext.request
import com.rz.commonlibrary.ext.requestT
import com.xk.eyepetizer.mvp.model.bean.KzCategory
import tianyinews.tianyi.com.network.request.HomeRequestCoroutine
import tianyinews.tianyi.com.tianyinews.bean.CategoryHotItem
import tianyinews.tianyi.com.tianyinews.bean.KzCategoryHot
import tianyinews.tianyi.com.tianyinews.bean.ListDataUiState
import tianyinews.tianyi.com.tianyinews.bean.VideItem

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/9 2:47 下午
 * @Description:
 */
class KaiyanHotViewModel : BaseViewModel() {

    var liveData: MutableLiveData<ListDataUiState<CategoryHotItem>> = MutableLiveData()

    fun requestData() {
        requestT({
            HomeRequestCoroutine.getHotCategoryData()
        }, {
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = true,
                    isEmpty = it.tabInfo?.tabList?.isEmpty()?:true,
//                    hasMore = it.hasMore(),
                    isFirstEmpty = it.tabInfo?.tabList?.isEmpty()?:true,
                    listData = it.tabInfo?.tabList
                )
            liveData.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = true,
                    listData = arrayListOf<CategoryHotItem>()
                )
            liveData.value = listDataUiState
        })
    }
}