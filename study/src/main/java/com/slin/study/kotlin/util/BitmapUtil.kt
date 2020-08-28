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
import androidx.annotation.FloatRange


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
     * 裁剪缩放图片以适应宽高比
     * @param bitmap origin bitmap
     * @param width     目标 宽
     * @param height    目标 高
     */
    fun cropBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        if (width <= 0 || height <= 0) {
            return bitmap
        }

        val w = bitmap.width
        val h = bitmap.height

        var cropWidth: Int = w
        var cropHeight: Int = h

        val ratio: Float = width.toFloat() / height

        val matrix = Matrix()

        if (w / h > ratio) {
            if (h > height) {
                val scale = height / cropHeight.toFloat()
                matrix.setScale(scale, scale)
            }
            cropWidth = (cropHeight * ratio).toInt()
        } else {
            if (w > width) {
                val scale = width / cropWidth.toFloat()
                matrix.setScale(scale, scale)
            }
            cropHeight = (cropWidth / ratio).toInt()
        }

        /**
         * 最终图片的宽高是cropWidth和cropHeight乘以matrix里面的缩放比
         */
        val b = Bitmap.createBitmap(bitmap, 0, 0, cropWidth, cropHeight, matrix, false)
        Log.d(
            TAG,
            "cropBitmap: w = $w h = $h width = $width height = $height " +
                    " cropWidth = $cropWidth cropHeight = $cropHeight b.width = ${b.width} b.height = ${b.height}  matrix = $matrix"
        )
        return b
    }

    /**
     * 高斯模糊
     */
    fun gaussianBlur(
        context: Context,
        inBitmap: Bitmap,
        @FloatRange(fromInclusive = false, from = 0.0, to = 25.0) radius: Float = 15f
    ): Bitmap {
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
        if (width > 0 && height > 0) {
            //设置只获取宽高
            option.inJustDecodeBounds = true
            BitmapFactory.decodeResource(resource, imageRes, option)
            //计算合适的比例 2的倍数
            option.inSampleSize = calculateInSampleSize(option, width, height)
        }
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
        options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int
    ): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight
                && halfWidth / inSampleSize >= reqWidth
            ) {
                inSampleSize *= 2
            }
        }
        Log.d(TAG, "calculateInSampleSize: $inSampleSize")
        return inSampleSize
    }

}

