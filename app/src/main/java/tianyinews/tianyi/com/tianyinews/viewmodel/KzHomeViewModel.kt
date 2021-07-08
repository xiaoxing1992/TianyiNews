package tianyinews.tianyi.com.tianyinews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rz.commonlibrary.base.viewmodel.BaseViewModel
import com.rz.commonlibrary.ext.request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tianyinews.tianyi.com.network.request.HomeRequestCoroutine
import tianyinews.tianyi.com.tianyinews.adapter.KaiyanRecommendAdapter
import tianyinews.tianyi.com.tianyinews.bean.*

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/7 5:55 下午
 * @Description:
 */
class KzHomeViewModel : BaseViewModel() {

    var bannerDataState: MutableLiveData<MutableList<VideItem>> = MutableLiveData()
    var homeDataState: MutableLiveData<ListDataUiState<VideItem>> = MutableLiveData()
    var moreDataState: MutableLiveData<ListDataUiState<VideItem>> = MutableLiveData()
    var bannerHomeBean: VideoApiResponse<List<VideoIssue>>? = null
    private var nextPageUrl: String? = null

    fun requestData() {

        viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    bannerHomeBean = HomeRequestCoroutine.getVideoData()
                    bannerHomeBean!!.issueList.get(0)?.itemList?.filter { item -> item.type == "banner2" || item.type == "textHeader" }
                        ?.forEach { item -> bannerHomeBean!!.issueList.get(0)?.itemList?.remove(item) }
                    HomeRequestCoroutine.getVideoMoreData(bannerHomeBean!!.nextPageUrl)
                }
            }.onSuccess {
                nextPageUrl = it.nextPageUrl

                val newItemList = it?.issueList.get(0)?.itemList
                newItemList?.filter { item -> item.type == "banner2"}?.forEach { item -> newItemList?.remove(item) }
                bannerHomeBean?.issueList!![0].itemList.addAll(newItemList)

               val bannerList =  mutableListOf<VideItem>()
                bannerHomeBean?.issueList!![0].itemList.forEachIndexed { index, videItem ->
                    if(videItem.type=="video"&&index<6){
                        bannerList.add(videItem)
                    }
                }

                bannerDataState.value = bannerList
                if(bannerHomeBean?.issueList!![0].itemList.size>5){
                    val subList = bannerHomeBean?.issueList!![0].itemList.subList(5, bannerHomeBean?.issueList!![0].itemList.size)
                    bannerHomeBean?.issueList!![0].itemList =subList.toMutableList()
                }

                //请求成功
                val listDataUiState =
                    ListDataUiState(
                        isSuccess = true,
                        isRefresh = true,
                        isEmpty = bannerHomeBean?.issueList!!.get(0)?.itemList.isEmpty(),
//                    hasMore = it.hasMore(),
                        isFirstEmpty = bannerHomeBean?.issueList!!.get(0)?.itemList?.isEmpty() ?: true,
                        listData = bannerHomeBean?.issueList!!.get(0)?.itemList ?: mutableListOf()
                    )
                homeDataState.value = listDataUiState
            }.onFailure {
                //请求失败
                val listDataUiState =
                    ListDataUiState(
                        isSuccess = false,
                        errMessage = "ss",
                        isRefresh = true,
                        listData = arrayListOf<VideItem>()
                    )
                homeDataState.value = listDataUiState
            }
        }
    }

    fun requestMoreData(){
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    nextPageUrl?.let {
                        HomeRequestCoroutine.getVideoMoreData(it)
                    }
                }
            }.onSuccess {
                nextPageUrl = it?.nextPageUrl

                val newItemList = it?.issueList?.get(0)?.itemList
                newItemList?.filter { item -> item.type == "banner2"}?.forEach { item -> newItemList?.remove(item) }

                //请求成功
                val listDataUiState =
                    ListDataUiState(
                        isSuccess = true,
                        isRefresh = true,
                        isEmpty = it?.issueList!!.get(0)?.itemList.isEmpty(),
//                    hasMore = it.hasMore(),
                        isFirstEmpty = it?.issueList!!.get(0)?.itemList?.isEmpty() ?: true,
                        listData = it?.issueList!!.get(0)?.itemList ?: mutableListOf()
                    )
                moreDataState.value = listDataUiState
            }.onFailure {
                //请求失败
                val listDataUiState =
                    ListDataUiState(
                        isSuccess = false,
                        errMessage = "ss",
                        isRefresh = true,
                        listData = arrayListOf<VideItem>()
                    )
                moreDataState.value = listDataUiState
            }
        }
    }
}