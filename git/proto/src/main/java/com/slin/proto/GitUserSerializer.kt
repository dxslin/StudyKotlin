package com.slin.proto

import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream

/**
 * author: slin
 * date: 2020-11-30
 * description: 序列化
 */
object GitUserSerializer : Serializer<GitUserPbOuterClass.GitUserPb> {

    override suspend fun readFrom(input: InputStream): GitUserPbOuterClass.GitUserPb {
        return GitUserPbOuterClass.GitUserPb.parseFrom(input)
    }

    override suspend fun writeTo(t: GitUserPbOuterClass.GitUserPb, output: OutputStream) {
        t.writeTo(output)
    }

    override val defaultValue: GitUserPbOuterClass.GitUserPb
        get() = GitUserPbOuterClass.GitUserPb.getDefaultInstance()
}