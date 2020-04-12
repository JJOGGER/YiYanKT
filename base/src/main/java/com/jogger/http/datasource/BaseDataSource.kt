package com.jogger.http.datasource

import com.jogger.http.basic.RetrofitManager
import com.jogger.http.service.ApiService
open class BaseDataSource {
     companion object{
          const val APP_VERSION = "3.35"
     }
     val mService: ApiService = RetrofitManager.getService(ApiService::class.java)
}