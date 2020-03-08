package com.jogger.entity

data class UserData(
     val uid: String? = null,
     val username: String? = null,
     val device: String? = null,
     val gender: String? = null,
     val account: String? = null,
     val actype: Int=0,
     val fanscnt: Int=0,
     val followcnt:Int=0,
     val smallavatar: String? = null,
     val largeavatar: String? = null
)