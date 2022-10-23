package com.slin.study.kotlin.util

import android.os.Environment
import android.provider.MediaStore
import com.dylanc.longan.insertMediaDownload
import com.dylanc.longan.openOutputStream
import com.slin.study.kotlin.StudyKotlinApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.IOException
import java.net.URLConnection

sealed class CopyState {
    object Init : CopyState()
    data class Copying(val index: Int, val total: Int) : CopyState()
    data class Success(val dir: String) : CopyState()
    data class Fail(val index: Int, val total: Int, val e: IOException) : CopyState()
}

object AssetsUtils {

    private val context inline get() = StudyKotlinApplication.INSTANCE

    /**
     * 将assets文件复制到download
     */
    fun copyAssetsToDownload(dir: String): Flow<CopyState> = flow {
        var index = 0
        var total = 0
        try {
            emit(CopyState.Init)
            val outDir = "${Environment.DIRECTORY_DOWNLOADS}${File.separator}$dir"
            val assets = context.assets
            assets.list(dir)?.also { filenames ->
                total = filenames.size
                emit(CopyState.Copying(index, filenames.size))
                filenames.forEach {
                    val source = assets.open("$dir/$it")
                    context.contentResolver.insertMediaDownload(
                        Pair(MediaStore.Files.FileColumns.DISPLAY_NAME, it),
                        Pair(MediaStore.Files.FileColumns.RELATIVE_PATH, outDir),
                        Pair(MediaStore.Files.FileColumns.TITLE, it),
                        Pair(
                            MediaStore.Files.FileColumns.MIME_TYPE,
                            URLConnection.guessContentTypeFromName(it),
                        )
                    )?.openOutputStream { sink ->
                        source.use {
                            sink.use {
                                source.copyTo(sink)
                            }
                        }
                    }
                    emit(CopyState.Copying(++index, total))
                }
                emit(CopyState.Success(outDir))
            } ?: emit(CopyState.Success(outDir))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(CopyState.Fail(index, total, e))
        }
    }
}
