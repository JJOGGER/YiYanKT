package com.jogger.entity

/**
 * Created by jogger on 2020/3/17
 * 描述：
 */
data class SinaStatus(
    val created_at: String? = null,
    val id: Long? = null,
    val text: String? = null,
    val source: String? = null,
    val favorited: Boolean? = null,
    val truncated: Boolean? = null,
    val in_reply_to_status_id: String? = null,
    val in_reply_to_user_id: String? = null,
    val in_reply_to_screen_name: String? = null,
    val geo: String? = null,
    val mid: String? = null,
    val annotations: List<String>? = null,
    val reposts_count: Int? = null,
    val comments_count: Int? = null
)