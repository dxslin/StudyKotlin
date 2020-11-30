package com.slin.proto

import androidx.datastore.Serializer
import java.io.InputStream
import java.io.OutputStream

/**
 * author: slin
 * date: 2020-11-30
 * description: 序列化
 */
object GitUserSerializer : Serializer<GitUserPrefOuterClass.GitUserPref> {
    override fun readFrom(input: InputStream): GitUserPrefOuterClass.GitUserPref {
        return GitUserPrefOuterClass.GitUserPref.parseFrom(input)
    }

    override fun writeTo(t: GitUserPrefOuterClass.GitUserPref, output: OutputStream) {
        t.writeTo(output)
    }
}