package com.jogger.module_star.viewmodel

import com.jogger.base.BaseViewModel
import com.jogger.constant.CARD_CATEGORY
import com.jogger.entity.ArticleData
import com.jogger.http.datasource.HomeDataSource
import kotlinx.coroutines.CoroutineScope

/**
 * Created by jogger on 2020/3/4
 * 描述：
 */
class StarViewModel : BaseViewModel() {
    fun getTextCardsByType(type: Int) {
        var block: suspend CoroutineScope.() -> ArticleData
        when (type) {

            CARD_CATEGORY.TYPE_ALL._value -> {
                block = { HomeDataSource.getAllStarTextCards() }
            }
            CARD_CATEGORY.TYPE_ORIGIN._value -> {
                block = { HomeDataSource.getOriginStarTextCards() }
            }
            else ->
                block = { HomeDataSource.getTextCardsByType(type) }
        }
        launchOnlyresult(block, {

        }, {

        })
    }
}