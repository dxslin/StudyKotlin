package com.slin.study.kotlin.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import androidx.annotation.DrawableRes
import kotlin.math.min
import kotlin.math.roundToInt


/**
 * author: slin
 * date: 2020/8/7
 * description:
 *
 */

/**
 * 高斯模糊
 */
object BitmapUtil {

    private val TAG: String? = BitmapUtil::class.simpleName


    /**
     * 按比例缩放图片
     *
     * @param origin 原图
     * @param ratio  比例
     * @return 新的bitmap
     */
    fun scaleBitmap(origin: Bitmap, vwidth: Int, vheight: Int): Bitmap {
        val dwidth = origin.width
        val dheight = origin.height

        var scale = 0f
        var dx = 0f
        var dy = 0f
        if (dwidth * vheight > vwidth * dheight) {
            scale = vheight.toFloat() / dheight.toFloat()
            dx = (vwidth - dwidth * scale) * 0.5f
        } else {
            scale = vwidth.toFloat() / dwidth.toFloat()
            dy = (vheight - dheight * scale) * 0.5f
        }

        val matrix = Matrix()
        matrix.setScale(scale, scale)
        matrix.postTranslate(dx.roundToInt().toFloat(), dy.roundToInt().toFloat())

        Log.d(TAG, "scaleBitmap: scale=$scale dx = $dx dy = $dy")
        return Bitmap.createBitmap(origin, 0, 0, dwidth, dheight, matrix, false)
    }

    fun cropBitmapTop(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        if (width <= 0 || height <= 0) {
            return bitmap
        }

        val w = bitmap.width
        val h = bitmap.height
        val cropWidth = min(w, width)
        val cropHeight = min(h.toFloat(), height.toFloat() / width * cropWidth).toInt()

        return Bitmap.createBitmap(bitmap, 0, 0, cropWidth, cropHeight)
    }

    /**
     * 高斯模糊
     */
    fun gaussianBlur(context: Context, inBitmap: Bitmap, radius: Float = 15f): Bitmap {
        val outBitmap = Bitmap.createBitmap(inBitmap)

        val renderScript = RenderScript.create(context)
        val scriptIntrinsicBlur =
            ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))

        //根据输入输出Bitmap分配内存
        val tmpIn = Allocation.createFromBitmap(renderScript, inBitmap)
        val tmpOut = Allocation.createFromBitmap(renderScript, outBitmap)
        //设置模糊半径取值0-25之间，不同半径得到的模糊效果不同
        scriptIntrinsicBlur.setRadius(radius)
        scriptIntrinsicBlur.setInput(tmpIn)
        scriptIntrinsicBlur.forEach(tmpOut)

        tmpOut.copyTo(outBitmap)
        return outBitmap
    }

    /**
     * 获取bitmap资源
     */
    fun decodeBitmap(
        resource: Resources,
        @DrawableRes imageRes: Int,
        width: Int,
        height: Int
    ): Bitmap {
        val option = BitmapFactory.Options()
        //设置只获取宽高
        option.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resource, imageRes, option)
        //计算合适的比例 2的倍数
        option.inSampleSize = calculateInSampleSize(option, width, height)
        //设置加载资源
        option.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(resource, imageRes, option)
    }

    fun decodeBitmap(path: String, width: Int, height: Int): Bitmap {
        val option = BitmapFactory.Options()
        //设置只获取宽高
        option.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, option)
        //计算合适的比例 2的倍数
        option.inSampleSize = calculateInSampleSize(option, width, height)
        //设置加载资源
        option.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(path, option)
    }

    /**
     * 计算 bitmap 合适的压缩比例
     */
    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        desWidth: Int,
        desHeight: Int
    ): Int {
        val width = options.outWidth
        val height = options.outHeight
        var sampleSize = 1
        if (height > desHeight || width > desHeight) {
            val halfWidth = width / 2
            val halfHeight = height / 2
            while (halfHeight / sampleSize > desHeight && halfWidth / sampleSize > halfWidth) {
                sampleSize *= 2
            }
        }
        return sampleSize
    }

}

