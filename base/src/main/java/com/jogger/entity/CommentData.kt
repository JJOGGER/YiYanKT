package com.jogger.entity

class CommentData(
    val commentid: String? = null,
    val content: String? = null,
    val creator: UserData? = null,
    val datetime: String? = null,//datetime=2020-03-29 20:59:51.0
    val likecnt: Int = 0,
    val textcard: TextCard? = null,
    val type: Int? = null
)