package com.jogger.entity


data class TextCard(
    val picpath: String? = null,
    val ava: String? = null,
    val commentcnt: Int = 0,
    val commercialtag: String? = null,
    val from: String? = null,
    val originbook: OriginBook? = null,//创建所在书籍
    val textcardid: String? = null,
    val type: String? = null,//yyh_0_1_2_0
    val collectcnt: Int = 0,
    val creator: UserData? = null,
    val content: String? = null,
    val feedid: String? = null,
    val category: Int? = null,
    val title: String? = null,
    val rec: String? = null,
    val original: String? = null,
    val showtime: String? = null,
    val priv: String? = null,
    val replycnt: Int = 0,
    val datetime: String? = null
)