package com.jogger.http.basic

import android.annotation.SuppressLint
import com.jogger.base.BuildConfig
import com.jogger.http.basic.config.getBaseUrl
import com.jogger.http.interceptor.HeaderInterceptor
import com.jogger.http.interceptor.RetryIntercepter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Created by jogger on 2020/2/26
 * 描述：
 */
object RetrofitManager {
    private const val READ_TIMEOUT: Long = 10000
    private const val WRITE_TIMEOUT: Long = 10000
    private const val CONNECT_TIMEOUT: Long = 10000
    private var mServiceMap: ConcurrentHashMap<String, Any> = ConcurrentHashMap()


    fun <T> getService(clz: Class<T>): T {
        return getService(clz, getBaseUrl())
    }

    fun <T> getService(clz: Class<T>, host: String): T {
        val value: T
        if (mServiceMap.containsKey(host)) {
            val obj = mServiceMap.get(host)
            if (obj == null) {
                //没有对应service，则创建
                value = createRetrofit(host).create(clz)
                mServiceMap.put(host, value!!)
            } else {
                value = obj as T
            }
        } else {
            value = createRetrofit(host).create(clz)
            mServiceMap.put(host, value!!)
        }
        return value
    }

    private fun createRetrofit(url: String): Retrofit {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(RetryIntercepter(3))
            .addInterceptor(HeaderInterceptor())
            .retryOnConnectionFailure(true)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(httpLoggingInterceptor)
        }
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            @Throws(CertificateException::class)
            override fun checkClientTrusted(
                chain: Array<X509Certificate>,
                authType: String
            ) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            @Throws(CertificateException::class)
            override fun checkServerTrusted(
                chain: Array<X509Certificate>,
                authType: String
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate?> {
                return arrayOfNulls(0)
            }
        })
        var client: OkHttpClient
        try {
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            client = builder.sslSocketFactory(sslContext.socketFactory)
                .hostnameVerifier { hostname, session -> true }.build()
        } catch (e: Exception) {
            e.printStackTrace()
            client = builder.build()
        }
        return Retrofit.Builder()
            .client(client)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

//    fun <T> applySchedulers(): ObservableTransformer<BaseResponse<T>, T> {
//        return ObservableTransformer {
//            it.subscribeOn(Schedulers.io())
//                .flatMap({ result ->
//                    when (result.errorcode) {
//                        200 -> createData(result.data)
//                        else -> createError(result.errorcode, result.msg!!)
//                    }
//                })
//        }
//    }
//
//    fun <T> createData(t: T): Observable<T> {
//        return Observable.create({ emitter ->
//            try {
//                emitter.onNext(t);
//                emitter.onComplete()
//            } catch (e: Exception) {
//                emitter.onError(e)
//            }
//        })
//    }
//
//    fun <T> createError(code: Int, msg: String): Observable<T> {
//        return Observable.create({ emitter -> emitter.onError(ServerResultException(code, msg)) })
//    }

}

