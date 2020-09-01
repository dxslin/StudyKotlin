package com.slin.study.kotlin.ui.testlist

import android.app.Activity
import android.os.Parcel
import android.os.Parcelable


/**
 * author: slin
 * date: 2020/9/1
 * description:
 *
 */


data class TestPageData(val name: String, val icon: Int, var activityClass: Class<out Activity>?) :
    Parcelable {
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        null
    ) {
        val className = parcel.readString()
        try {
            @Suppress("UNCHECKED_CAST")
            activityClass = Class.forName(className) as Class<out Activity>?
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(icon)
        parcel.writeString(activityClass?.name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TestPageData> {
        override fun createFromParcel(parcel: Parcel): TestPageData {
            return TestPageData(parcel)
        }

        override fun newArray(size: Int): Array<TestPageData?> {
            return arrayOfNulls(size)
        }
    }
}
