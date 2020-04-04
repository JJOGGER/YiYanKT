package com.jogger.entity

import android.os.Parcel
import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity
import ex.readMutableList


data class TextCard(
    val picpath: String? = null,
    val MoreTagsData: MoreTagsData? = null,
    var subcardlist: MutableList<TextCard>?,
    val ava: String? = null,
    val commentcnt: Int = 0,
    val commercialtag: String? = null,
    val from: String? = null,
    val originbook: OriginBook? = null,//创建所在书籍
    val textcardid: String? = null,
    val type: String? = null,//yyh_0_1_2_0
    val collectcnt: Int = 0,
    val creator: UserData? = null,
    val content: String? = null,
    val feedid: String? = null,
    val category: Int = 0,
    val title: String? = null,
    val rec: String? = null,
    val original: Int = 0,
    val showtime: String? = null,
    val priv: String? = null,
    val replycnt: Int = 0,
    val datetime: String? = null,
    val dislikecnt: Int = 0
) : MultiItemEntity, Parcelable {
    override var itemType: Int = category
        set(category) {
            field = category
        }
        get() = category

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(com.jogger.entity.MoreTagsData::class.java.classLoader),
        parcel.readMutableList(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(OriginBook::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readParcelable(UserData::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(picpath)
        parcel.writeParcelable(MoreTagsData, flags)
        if (subcardlist == null)
            subcardlist = arrayListOf()
        parcel.writeList(subcardlist)
        parcel.writeString(ava)
        parcel.writeInt(commentcnt)
        parcel.writeString(commercialtag)
        parcel.writeString(from)
        parcel.writeParcelable(originbook, flags)
        parcel.writeString(textcardid)
        parcel.writeString(type)
        parcel.writeInt(collectcnt)
        parcel.writeParcelable(creator, flags)
        parcel.writeString(content)
        parcel.writeString(feedid)
        parcel.writeInt(category)
        parcel.writeString(title)
        parcel.writeString(rec)
        parcel.writeInt(original)
        parcel.writeString(showtime)
        parcel.writeString(priv)
        parcel.writeInt(replycnt)
        parcel.writeString(datetime)
        parcel.writeInt(dislikecnt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TextCard> {
        override fun createFromParcel(parcel: Parcel): TextCard {
            return TextCard(parcel)
        }

        override fun newArray(size: Int): Array<TextCard?> {
            return arrayOfNulls(size)
        }
    }

}

