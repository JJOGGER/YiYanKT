package com.jogger.entity

import android.os.Parcel
import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity

data class OriginBook(
    val picpath: String? = null,
    val bookid: String? = null,
    val ava: String? = null,
    var cardcnt: Int = 0,
    val bookname: String? = null,
    var isCheck: Boolean? = false
) : MultiItemEntity, Parcelable {
    override val itemType: Int
        get() = if (bookid == null) -1 else 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(picpath)
        parcel.writeString(bookid)
        parcel.writeString(ava)
        parcel.writeInt(cardcnt)
        parcel.writeString(bookname)
        parcel.writeValue(isCheck)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OriginBook> {
        override fun createFromParcel(parcel: Parcel): OriginBook {
            return OriginBook(parcel)
        }

        override fun newArray(size: Int): Array<OriginBook?> {
            return arrayOfNulls(size)
        }
    }

}

    