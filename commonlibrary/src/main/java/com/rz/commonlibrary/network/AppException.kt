package com.rz.commonlibrary.network

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/5 3:17 下午
 * @Description:    自定义错误信息异常
 */
class AppException:Exception {
    var errorMsg: String //错误消息
    var errCode: Int = 0 //错误码
    var errorLog: String? //错误日志

    constructor(errCode: Int, error: String?, errorLog: String? = "") : super(error) {
        this.errorMsg = error ?: "请求失败，请稍后再试"
        this.errCode = errCode
        this.errorLog = errorLog?:this.errorMsg
    }

    constructor(error: CError,e: Throwable?) {
        errCode = error.getKey()
        errorMsg = error.getValue()
        errorLog = e?.message
    }
}