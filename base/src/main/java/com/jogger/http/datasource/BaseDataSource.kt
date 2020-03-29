package com.jogger.http.datasource

import com.jogger.http.basic.RetrofitManager
import com.jogger.http.service.ApiService
const val APP_VERSION = "3.35"
open class BaseDataSource {
     val mService: ApiService = RetrofitManager.getService(ApiService::class.java)
}