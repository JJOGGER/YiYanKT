package com.jogger.http.service

import com.jogger.entity.ArticleData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {//Cookie: JSESSIONID=9CF61CDE1A602835A152B72B8967A2CE

    @POST("/yiyan/getfeeds")//"v","3.35"
    suspend fun getSubcribeArticles(
        @Query("v") v: String,
        @Query("ltid") feedid: String?
    ): ArticleData

    @GET("/yiyan/getalltextcard")
    suspend fun getAllStarTextCards(@Query("v") v: String): ArticleData

    @GET("/yiyan/getoriginaltextcard")
    suspend fun getOriginStarTextCards(@Query("v") v: String): ArticleData

    @GET("/yiyan/getalltextcard")
    suspend fun getStarTextCardsByType(
        @Query("v") v: String,
        @Query("category") category: Int
    ): ArticleData

    @GET("/yiyan/search")
    suspend fun searchTextCards(
        @Query("v") v: String,
        @Query("c") c: String
    )

    @GET("/yiyan/search")
    suspend fun searchTextUsers(
        @Query("v") v: String,
        @Query("c") c: String
    )

    @GET("/yiyan/checkliked")
    suspend fun checkLiked(
        @Query("v") v: String,
        @Query("cardid") c: String
    )//"l:0

    @POST("/yiyan/newlike")
    suspend fun newLike(
        @Query("v") v: String,
        @Query("cardid") c: String
    )

    @POST("/yiyan/cancellike")
    suspend fun cancelLike(
        @Query("v") v: String,
        @Query("cardid") c: String
    )

    @GET("/yiyan/getnoticecount")
    suspend fun getNoticeCount(
        @Query("v") v: String,
        @Query("datetime") datetime: String
    )

    //只看评论
    @GET("/yiyan/getnoticelist")
    suspend fun getNoticeList(
        @Query("v") v: String,
        @Query("cmt") cmt: Int
    )

    //用户首页信息
    @GET("/yiyan/getuserinfoandbooklist")
    suspend fun getUserinfoAndBooklist(
        @Query("v") v: String,
        @Query("uid") uid: String
    )

    //用户首页字句
    @GET("/yiyan/gettextcardbyuser")
    suspend fun getTextCardByUser(
        @Query("v") v: String,
        @Query("uid") uid: String
    )

    //用户首页字句(只看自定义卡片)
    @GET("/yiyan/gettextcardbyuser")
    suspend fun getTextCardByUser(
        @Query("v") v: String,
        @Query("uid") uid: String,
        @Query("jo") jo: Int
    )

    //我同感的
    @POST("/yiyan/getcommentbyuser")
    suspend fun getCommentByuser(
        @Query("v") v: String,
        @Query("u") uid: String,
        @Query("ml") ml: Int
    )

    //单条卡片信息
    @GET("/yiyan/gettextcard")
    suspend fun getTextCard(
        @Query("v") v: String,
        @Query("cardid") cardid: String
    )

    //发布卡片
    @POST("/yiyan/newtextcard_np")
    suspend fun publishTextCard(
        @Query("v") v: String,
        @Query("priv") priv: Int,//是否私密
        @Query("title") title: String,
        @Query("category") category: Int,
        @Query("original") original: Int,
        @Query("from") from: String,
        @Query("content") content: String,
        @Query("type") type: String,//yyv_0_0_0_0
        @Query("originbookid") originbookid: String
    )

    @POST("/yiyan/deletecard")
    suspend fun deleteTextCard(
        @Query("v") v: String,
        @Query("b") b: String,
        @Query("c") c: String,
        @Query("cardid") cardid: String
    )

    @POST("/yiyan/newbook_np")
    suspend fun createNewBook(
        @Query("v") v: String,
        @Query("bookname") bookname: String
    )
}