package com.jogger.http.service

import com.jogger.entity.ArticleData
import com.jogger.entity.LoginResult
import com.jogger.entity.SinaUserData
import com.jogger.entity.UsersData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {//Cookie: JSESSIONID=9CF61CDE1A602835A152B72B8967A2CE

    @GET("https://api.weibo.com/2/users/show.json")
    suspend fun getOtherPlatformUserInfo(
        @Query("access_token") accessToken: String,
        @Query("uid") uid: String
    ): SinaUserData

    @POST("/yiyan/loginfromotherplatform")
    suspend fun loginFromOtherPlatform(
        @Query("v") v: String,
        @Query("platuid") platuid: String,
        @Query("username") username: String,
        @Query("smallavatar") smallavatar: String,
        @Query("largeavatar") largeavatar: String,
        @Query("gender") gender: String,
        @Query("ac") ac: String,
        @Query("actype") actype: String
    ): LoginResult

    @POST("/yiyan/getfeeds")//"v","3.35"
    suspend fun getSubcribeArticles(
        @Query("v") v: String,
        @Query("ltid") feedid: String?
    ): ArticleData

    @GET("/yiyan/getalltextcard")
    suspend fun getAllStarTextCards(
        @Query("v") v: String,
        @Query("i") lastCardId: String?,
        @Query("moreextra") moreextra: String?
    ): ArticleData

    @GET("/yiyan/getoriginaltextcard")
    suspend fun getOriginStarTextCards(
        @Query("v") v: String,
        @Query("i") lastCardId: String?
    ): ArticleData

    @GET("/yiyan/getalltextcard")
    suspend fun getStarTextCardsByType(
        @Query("v") v: String,
        @Query("i") lastCardId: String?,
        @Query("moreextra") moreextra: String?,//{"afterid":"4829927"}
        @Query("category") category: Int
    ): ArticleData

    /**
     * 搜索卡片
     */
    @GET("/yiyan/search")
    suspend fun searchTextCards(
        @Query("v") v: String,
        @Query("c") c: String,
        @Query("i") i: Int?,//上一个moreextra的值
        @Query("moreextra") moreextra: String?//{"offset":"20"}
    ): ArticleData

    /**
     * 搜索用户
     */
    @GET("/yiyan/search")
    suspend fun searchUsers(
        @Query("v") v: String,
        @Query("u") u: String
    ): UsersData

    @GET("/yiyan/search")
    suspend fun searchUsers(
        @Query("v") v: String,
        @Query("u") u: String,
        @Query("i") i: Int?
    ): UsersData

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

    /**
     * 文集排序
     */
    @POST("/yiyan/updatebooksorder")
    suspend fun updateBooksOrder(
        @Query("v") v: String,
        @Query("booksorder") booksorder: String//2308828,2183042
    )
}