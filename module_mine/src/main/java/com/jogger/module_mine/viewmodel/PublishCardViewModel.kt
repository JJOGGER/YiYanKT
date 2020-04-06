package com.jogger.module_mine.viewmodel

import android.text.TextUtils
import com.jogger.base.BaseViewModel
import com.jogger.entity.request.PublishTextCardRequest
import com.jogger.http.datasource.ArticleActionDataSource
import com.jogger.utils.LogUtils
import ex.CosService

class PublishCardViewModel : BaseViewModel() {
    fun publishCard(request: PublishTextCardRequest) {
        defUI.showDialog.call()
        if (!TextUtils.isEmpty(request.pic)) {
            with(CosService()) {
                setCosServiceResultListener(object : CosService.CosServiceResultListener {
                    override fun onSuccess(path: String) {
                        request.pic = path
                        request(request)
                    }

                    override fun onFailure() {
                    }

                })
                uploadImg(request.pic!!)
            }
            return
        }
        request(request)

    }

    fun request(request: PublishTextCardRequest) {
        launchOnlyresult({
            LogUtils.e("------${request}")
            ArticleActionDataSource.publishTextCard(
                request.priv,
                request.title,
                request.category,
                request.original,
                request.from,
                request.content,
                request.type,
                request.originbookid
            )
        }, {
            defUI.dismissDialog
        }, { defUI.dismissDialog}, { defUI.dismissDialog}, true)
    }
}