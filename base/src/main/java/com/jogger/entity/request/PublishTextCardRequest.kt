package com.jogger.entity.request

import android.os.Parcel
import android.os.Parcelable
import com.jogger.constant.CARD_CATEGORY

data class PublishTextCardRequest(
    var priv: Int = 0,//是否私密
    var title: String? = null,
    var category: Int = CARD_CATEGORY.TYPE_TEXT._value,
    var original: Int = 0,
    var from: String? = null,
    var content: String? = null,
    var type: String? = null,//yyv_0_0_0_0
    var pic: String? = null,
    var originbookid: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(priv)
        parcel.writeString(title)
        parcel.writeInt(category)
        parcel.writeInt(original)
        parcel.writeString(from)
        parcel.writeString(content)
        parcel.writeString(type)
        parcel.writeString(pic)
        parcel.writeString(originbookid)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "PublishTextCardRequest(priv=$priv, title=$title, category=$category, original=$original, from=$from, content=$content, type=$type, pic=$pic, originbookid=$originbookid)"
    }


    companion object CREATOR : Parcelable.Creator<PublishTextCardRequest> {
        override fun createFromParcel(parcel: Parcel): PublishTextCardRequest {
            return PublishTextCardRequest(parcel)
        }

        override fun newArray(size: Int): Array<PublishTextCardRequest?> {
            return arrayOfNulls(size)
        }
    }

}