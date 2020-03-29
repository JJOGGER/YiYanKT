package com.jogger.http.datasource

object HomeDataSource : BaseDataSource() {
    suspend fun getSubcribeArticles(moreextra: String?) = mService.getSubcribeArticles(APP_VERSION, moreextra)

    suspend fun getTextCardsByType(type: Int, lastCardId: String?, moreextra: String?) =
        mService.getStarTextCardsByType(APP_VERSION, lastCardId, moreextra, type)

    suspend fun getAllStarTextCards(lastCardId: String?, moreextra: String?) =
        mService.getAllStarTextCards(APP_VERSION, lastCardId, moreextra)

    suspend fun getOriginStarTextCards(lastCardId: String?) = mService.getOriginStarTextCards(APP_VERSION, lastCardId)
    suspend fun searchTextCards(content: String, index: Int?, moreextra: String?) =
        mService.searchTextCards(APP_VERSION, content, index, moreextra)

    suspend fun searchUsers(content: String) = mService.searchUsers(APP_VERSION, content)
    suspend fun searchUsers(content: String, index: Int?) = mService.searchUsers(APP_VERSION, content, index)

    suspend fun getCardInTopic(cardId: String, refreshextra: String?, moreextra: String?) =
        mService.getCardInTopic(APP_VERSION, cardId, refreshextra, moreextra)
}