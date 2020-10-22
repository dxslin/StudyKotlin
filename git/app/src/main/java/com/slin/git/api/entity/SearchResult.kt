package com.slin.git.api.entity

import com.google.gson.annotations.SerializedName


/**
 * author: slin
 * date: 2020/10/22
 * description:
 *
 */

data class SearchResult<M>(
    @SerializedName("total_count")
    var totalCount: Int = 0,
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean = false,
    var items: List<M> = listOf()
)
//    : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readInt(),
//        parcel.readByte() != 0.toByte(),
//    ) {
//        items = ArrayList()
//        parcel.readList(items, SearchResult::class.java.classLoader)
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeInt(totalCount)
//        parcel.writeByte(if (incompleteResults) 1 else 0)
//        parcel.writeList(items)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<SearchResult<*>> {
//        override fun createFromParcel(parcel: Parcel): SearchResult<*> {
//            return SearchResult<Any>(parcel)
//        }
//
//        override fun newArray(size: Int): Array<SearchResult<*>?> {
//            return arrayOfNulls(size)
//        }
//    }
//
//
//}
