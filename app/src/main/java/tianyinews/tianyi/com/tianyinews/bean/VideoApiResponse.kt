package tianyinews.tianyi.com.tianyinews.bean

import com.rz.commonlibrary.network.BaseResponse

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/7 4:28 下午
 * @Description:
 */
data class VideoApiResponse<T>(
    var issueList: T,
    var nextPageUrl: String = "",
    var nextPublishTime: Long = 0,
    var newestIssueType: String = "",
    var dialog: Any = ""
) : BaseResponse<T>() {

    // 这里是示例，wanandroid 网站返回的 错误码为 0 就代表请求成功，请你根据自己的业务需求来改变
    override fun isSucces() = true

    override fun getResponseCode() = 10000

    override fun getResponseData() = issueList

    override fun getResponseMsg() = ""
}