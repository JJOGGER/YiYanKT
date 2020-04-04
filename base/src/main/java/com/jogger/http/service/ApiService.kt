package com.jogger.http.service

import com.jogger.entity.*
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

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
    ): UserHomeData

    @POST("/yiyan/getfeeds")
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
     * 穿越卡片列表
     */
    @GET("yiyan/crosstime")
    suspend fun getCrossTimeCards(
        @Query("v") v: String,
        @Query("cross") cross: Int?,
        @Query("datetime") datetime: String?,
        @Query("moreextra") moreExtra: String?//{"ltid":"10768220"}
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
    ): CheckLikeResponse

    /**
     * 喜欢
     */
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
    ): UserHomeData

    //用户首页字句
    @GET("/yiyan/gettextcardbyuser")
    suspend fun getTextCardByUser(
        @Query("v") v: String,
        @Query("uid") uid: String,
        @Query("datetime") dateTime: String?,//最后一条的时间2020-03-04 12:29:40
        @Query("jo") jo: Int?//(1只看自定义卡片)
    ): ArticleData

    @GET("/yiyan/gettextcardsinbook")
    suspend fun getTextCardByBook(
        @Query("v") v: String,
        @Query("bookid") bookId: String,
        @Query("datetime") dateTime: String?//最后一条的时间2020-03-04 12:29:40
    ): ArticleData

    //用户主页我同感的
    @POST("/yiyan/getcommentbyuser")
    suspend fun getCommentByuser(
        @Query("v") v: String,
        @Query("u") uid: String,
        @Query("ml") ml: Int?,//1表示同感列表，其他用户ml mc不传默认同感
        @Query("mc") mc: Int?,//1表示评论列表
        @Query("ltid") lastCommentId: String?
    ): CommentResponse

    /**
     * 用户首页我的订阅列表
     */
    @GET("yiyan/getwriters")
    suspend fun getwriters(
        @Query("v") v: String,
        @Query("uid") uid: String,
        @Query("datetime") dateTime: String?,//2020-03-08 12:05:56
        @Query("i") i: Int?
    ): UsersData

    /**
     * 订阅用户
     */
    @POST("yiyan/follow")
    suspend fun followUser(
        @Query("v") v: String,
        @Query("fid") uid: String,
        @Query("cancel") cancel: Int?//1取消订阅
    )

    //读者列表
    @GET("yiyan/getfollowers")
    suspend fun getFollowers(
        @Query("v") v: String,
        @Query("uid") uid: String,
        @Query("datetime") dateTime: String?,//当前时间2020-03-08 12:05:56
        @Query("i") i: Int?//当前加载的条数
    ): UsersData

    //单条卡片信息
    @GET("/yiyan/gettextcard")
    suspend fun getTextCard(
        @Query("v") v: String,
        @Query("cardid") cardid: String
    ): TextCard?

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


    @GET("yiyan/getcardintopic")
    suspend fun getCardInTopic(
        @Query("v") v: String,
        @Query("cardid") cardId: String,
        @Query("refreshextra") refreshextra: String?,//为空表示所有话题留言 ，{"hot":"1"}表示热门留言
        @Query("moreextra") moreextra: String?
    ): ArticleData

    /**
     * 获取感悟列表
     */
    @POST("yiyan/getcommentbycard_jc")
    suspend fun getCommentsByCard(
        @Query("v") v: String,
        @Query("cardid") cardId: String,
        @Query("hot") hot: Int?,//为1表示获取更多评论
        @Query("ltid") lastCommentId: String?
    ): CommentResponse

    /**
     * 发表评论
     */
    @POST("yiyan/newcomment")
    suspend fun newComment(
        @Query("v") v: String,
        @Query("cardid") cardId: String,
        @Query("receiverid") receiverId: String?,
        @Query("content") content: String?
    ): CommentData

    @POST("yiyan/deletecomment")
    suspend fun deleteComment(
        @Query("v") v: String,
        @Query("commentid") commentId: String
    )

    /**
     * 获取帖子详情同感列表
     */
    @POST("yiyan/getcommentbycard")
    suspend fun getUserLikeds(
        @Query("v") v: String,
        @Query("cardid") cardId: String,
        @Query("ltid") lastCommentId: String?
    ): CommentResponse

    /**
     * 点赞别人的同感
     */
    @POST("yiyan/getcommentbycard")
    suspend fun likeComment(
        @Query("commentid") commentId: String,
        @Query("content") content: String,
        @Query("cancel") cancel: Int?//0点赞，1取消点赞
    ): CommentResponse

    /**
     * 获取book内容列表
     */
    @GET("yiyan/gettextcardsinbook?v=3.35&bookid=119")
    suspend fun likeComment(
        @Query("bookid") bookId: String?
    ): ArticleData

    /**
     * 收藏卡片
     */
    @POST("yiyan/collectcard")
    suspend fun collectCard(
        @Query("collectbookid") collectBookId: String?,
        @Query("collectbn") collectbn: String?,
        @Query("cardid") cardId: String?,
        @Query("collectuid") collectUid: String?,
        @Query("collectun") collectUserName: String?
    )


}