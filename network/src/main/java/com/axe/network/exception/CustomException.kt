package com.axe.network.exception

import android.net.ParseException
import com.google.gson.JsonParseException
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 本地异常处理。包括解析异常等其他异常
 */
object CustomException {
    /**
     * 未知错误
     */
    const val UNKNOWN = 1000

    /**
     * 解析错误
     */
    const val PARSE_ERROR = 1001

    /**
     * 网络错误
     */
    const val NETWORK_ERROR = 1002

    /**
     * 协议错误
     */
    const val HTTP_ERROR = 1003
    fun handleException(e: Throwable): ApiException {
        return if (e is JsonParseException
            || e is JSONException
            || e is ParseException
        ) {
            //解析错误
            ApiException(PARSE_ERROR, e.message!!)
        } else if (e is ConnectException) {
            //网络错误
            ApiException(NETWORK_ERROR, e.message!!)
        } else if (e is UnknownHostException || e is SocketTimeoutException) {
            //连接错误
            ApiException(NETWORK_ERROR, e.message!!)
        } else {
            //未知错误
            ApiException(UNKNOWN, e.message!!)
        }
    }
}