package com.slin.study.kotlin.ui.natively.mmap

import android.os.ParcelFileDescriptor

class ShareMemory(
    val fd: ParcelFileDescriptor,
    val size: Int,
) {
    override fun toString(): String {
        return "ShareMemory{" +
                "fd = $fd," +
                "size = $size}"
    }
}