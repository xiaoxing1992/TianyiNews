package com.rz.commonlibrary.network

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/5 3:20 下午
 * @Description:    根据异常返回相关的错误信息工具类
 */
object ExceptionHandle {
    fun handleException(e: Throwable?): AppException {
        val ex: AppException
        e?.let {
            when (it) {
                is HttpException -> {
                    ex = AppException(CError.NETWORK_ERROR,e)
                    return ex
                }
                is JsonParseException, is JSONException, is ParseException, is MalformedJsonException -> {
                    ex = AppException(CError.PARSE_ERROR,e)
                    return ex
                }
                is ConnectException -> {
                    ex = AppException(CError.NETWORK_ERROR,e)
                    return ex
                }
                is javax.net.ssl.SSLException -> {
                    ex = AppException(CError.SSL_ERROR,e)
                    return ex
                }
                is ConnectTimeoutException -> {
                    ex = AppException(CError.TIMEOUT_ERROR,e)
                    return ex
                }
                is java.net.SocketTimeoutException -> {
                    ex = AppException(CError.TIMEOUT_ERROR,e)
                    return ex
                }
                is java.net.UnknownHostException -> {
                    ex = AppException(CError.TIMEOUT_ERROR,e)
                    return ex
                }
                is AppException -> return it

                else -> {
                    ex = AppException(CError.UNKNOWN,e)
                    return ex
                }
            }
        }
        ex = AppException(CError.UNKNOWN,e)
        return ex
    }
}