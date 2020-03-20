package com.jogger.entity

/**
 * Created by jogger on 2020/3/17
 * 描述：
 */
data class LoginResult(
    val booklist: MutableList<OriginBook>? = null,
    val user: UserData? = null
){
    override fun toString(): String {
        return "LoginResult(booklist=$booklist, user=$user)"
    }
}