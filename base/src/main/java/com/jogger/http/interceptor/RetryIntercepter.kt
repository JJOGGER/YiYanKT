package com.jogger.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by jogger on 2020/2/26
 * 描述：
 */
class RetryIntercepter(maxRetry: Int) : Interceptor {
    private val maxRetry: Int = maxRetry
    var retryNum = 0
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        while (!response.isSuccessful && retryNum < maxRetry) {
            retryNum++
            response = chain.proceed(request)
        }
        return response
    }
}