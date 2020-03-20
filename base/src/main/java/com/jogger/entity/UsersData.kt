package com.jogger.entity

data class UsersData(
    val userlist: MutableList<UserData>? = null
) {
    override fun toString(): String {
        return "UsersData(userlist=$userlist)"
    }
}
