package tianyinews.tianyi.com.tianyinews.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rz.commonlibrary.base.viewmodel.BaseViewModel
import com.rz.commonlibrary.ext.request
import tianyinews.tianyi.com.network.request.HomeRequestCoroutine
import tianyinews.tianyi.com.tianyinews.bean.ListDataUiState
import tianyinews.tianyi.com.tianyinews.bean.NewsDataModel

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/5 2:05 下午
 * @Description:
 */
class HomeChildViewModel: BaseViewModel() {
    //页码 首页数据页码从0开始
    var pageNo = 1
    //首页文章列表数据
    var homeDataState: MutableLiveData<ListDataUiState<NewsDataModel>> = MutableLiveData()

    fun reuqestData(isRefresh: Boolean,type:String) {
        if (isRefresh) {
            pageNo = 1
        }

        request({
            HomeRequestCoroutine.getNewsList(type,pageNo)
        },{
            //请求成功
            pageNo++
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it?.data?.isEmpty() == true,
//                    hasMore = it.hasMore(),
                    isFirstEmpty = isRefresh && it?.data?.isEmpty() ?: true,
                    listData = it?.data?: mutableListOf()
                )
            homeDataState.value = listDataUiState
        },{
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<NewsDataModel>()
                )
            homeDataState.value = listDataUiState
        })
    }
}