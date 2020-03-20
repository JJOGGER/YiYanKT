package com.jogger.entity

/**
 * Created by jogger on 2020/3/17
 * 描述：
 */
data class SinaUserData(
    val id: Long? = null,
    val screen_name: String? = null,
    val name: String? = null,
    val province: String? = null,
    val city: String? = null,
    val location: String? = null,
    val description: String? = null,
    val url: String? = null,
    val profile_image_url: String? = null,
    val domain: String? = null,
    val gender: String? = null,
    val followers_count: Int? = null,
    val friends_count: Int? = null,
    val statuses_count: Int? = null,
    val favourites_count: Int? = null,
    val created_at: String? = null,
    val following: Boolean? = null,
    val allow_all_act_msg: Boolean? = null,
    val geo_enabled: Boolean? = null,
    val verified: Boolean? = null,
    val status: SinaStatus? = null,
    val allow_all_comment: Boolean? = null,
    val avatar_large: String? = null,
    val verified_reason: String? = null,
    val follow_me: Boolean? = null,
    val online_status: Int? = null,
    val bi_followers_count: Int? = null
)
