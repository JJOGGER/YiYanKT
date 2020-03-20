package com.jogger.entity

import android.os.Parcel
import android.os.Parcelable

class MoreTagsData (
    val l:String?=null,
    val type:String?=null,
    val i:String?=null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(l)
        parcel.writeString(type)
        parcel.writeString(i)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MoreTagsData> {
        override fun createFromParcel(parcel: Parcel): MoreTagsData {
            return MoreTagsData(parcel)
        }

        override fun newArray(size: Int): Array<MoreTagsData?> {
            return arrayOfNulls(size)
        }
    }
}