package com.axe.network.result

import com.axe.network.exception.ApiException


sealed class ResultResponse<out T : Any> {

    // 成功的状态
    data class Success<T : Any>(val data: T) : ResultResponse<T>()

    // 直接封装成code和Error的形式
    data class Error(val code: Int = 0, val error: String? = "") : ResultResponse<Nothing>()

    // 统一封装成ApiException
    data class Error2(val apiException: ApiException) : ResultResponse<Nothing>()

}