package com.slin.proto

import androidx.datastore.Serializer
import java.io.InputStream
import java.io.OutputStream

/**
 * author: slin
 * date: 2020-11-30
 * description: 序列化
 */
object GitUserSerializer : Serializer<GitUserPbOuterClass.GitUserPb> {
    override fun readFrom(input: InputStream): GitUserPbOuterClass.GitUserPb {
        return GitUserPbOuterClass.GitUserPb.parseFrom(input)
    }

    override fun writeTo(t: GitUserPbOuterClass.GitUserPb, output: OutputStream) {
        t.writeTo(output)
    }
}