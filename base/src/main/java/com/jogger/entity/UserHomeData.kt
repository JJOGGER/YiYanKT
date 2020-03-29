package com.jogger.entity

data class UserHomeData(
    val booklist:MutableList<OriginBook>?=null,
    val user:UserData?=null
)