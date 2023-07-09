package com.slin.study.kotlin.ui.natively.mmap

import java.util.Arrays

class ShareMemory(
    val fd: Int,
    val addr: LongArray,
    val size: Int,
) {
    override fun toString(): String {
        return "ShareMemory{" +
                "fd = $fd," +
                "addr = 0x${addr.joinToString { java.lang.Long.toHexString(it) }}," +
                "size = $size}"
    }
}