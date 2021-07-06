package tianyinews.tianyi.com.tianyinews.bean

import com.rz.commonlibrary.network.BaseResponse

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/5 3:59 下午
 * @Description:
 */
data class NewsApiResponse<T> (val error_code: Int, val reason: String, val result: T) : BaseResponse<T>() {

    // 这里是示例，wanandroid 网站返回的 错误码为 0 就代表请求成功，请你根据自己的业务需求来改变
    override fun isSucces() = error_code == 0

    override fun getResponseCode() = error_code

    override fun getResponseData() = result

    override fun getResponseMsg() = reason

}