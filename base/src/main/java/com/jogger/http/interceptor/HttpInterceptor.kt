package com.jogger.http.interceptor

import com.jogger.utils.LogUtils
import com.jogger.utils.MConfig
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * Created by jogger on 2020/3/16
 * 描述：
 */
class HttpInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalResponse: Response
        try {
            originalResponse = chain.proceed(request)
            val cookie = originalResponse.headers().values("Set-Cookie")
            if (!cookie.isEmpty()) {
                MConfig.setCookie(cookie.get(0))
            }
        } catch (e: Exception) {
            LogUtils.e("error:${e.message}")
            return chain.proceed(request)
        }

        val source = originalResponse?.body()!!.source()
        source.request(Int.MAX_VALUE.toLong())
        val byteString = source.buffer().snapshot().utf8()
        val responseBody = ResponseBody.create(null, byteString)
        return originalResponse.newBuilder().body(responseBody).build()
    }
}