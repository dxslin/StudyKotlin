package com.slin.core.image.glide

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.HttpException
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.util.ContentLengthInputStream
import com.slin.core.logger.loge
import okhttp3.*
import java.io.IOException
import java.io.InputStream

/**
 * author: slin
 * date: 2019-04-29
 * description:
 */
class OkHttpFetcher(private val client: OkHttpClient, private val url: GlideUrl) :
    DataFetcher<InputStream>, Callback {
    private var stream: InputStream? = null
    private var responseBody: ResponseBody? = null
    private var call: Call? = null
    private var callback: DataFetcher.DataCallback<in InputStream>? = null

    @Volatile
    private var isCancelled = false
    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        val requestBuilder = Request.Builder()
        requestBuilder.url(url.toStringUrl())
        for ((key, value) in url.headers) {
            requestBuilder.addHeader(key, value)
        }
        this.callback = callback
        call = client.newCall(requestBuilder.build())
        call?.enqueue(this)
    }

    override fun onFailure(call: Call, e: IOException) {
        loge(e) { "onFailure: OkHttp load failed, url: ${call.request().url}" }
        callback?.onLoadFailed(e)
    }

    @Throws(IOException::class)
    override fun onResponse(call: Call, response: Response) {
        responseBody = response.body
        if (response.isSuccessful) {
            val contentLength: Long = requireNotNull(responseBody).contentLength()
            stream = ContentLengthInputStream.obtain(
                requireNotNull(responseBody).byteStream(),
                contentLength
            )
            callback?.onDataReady(stream)
        } else {
            callback?.onLoadFailed(HttpException(response.message, response.code))
        }
    }

    override fun cleanup() {
        try {
            if (stream != null) {
                stream?.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (responseBody != null) {
            responseBody?.close()
        }
        callback = null
    }

    override fun cancel() {
        isCancelled = true
    }

    override fun getDataClass(): Class<InputStream> {
        return InputStream::class.java
    }

    override fun getDataSource(): DataSource {
        return DataSource.REMOTE
    }

    companion object {
        private val TAG = OkHttpFetcher::class.java.simpleName
    }
}