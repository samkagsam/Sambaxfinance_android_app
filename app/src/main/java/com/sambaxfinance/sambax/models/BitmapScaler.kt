package com.sambaxfinance.sambax.models

import android.graphics.Bitmap

object BitmapScaler {

    //scale and maintain aspect ratio given a desired width
    //BitmapScaler.scaleToFitWidth(bitmap, 100);
    fun scaleToFitWidth(b:Bitmap, width:Int):Bitmap{
        val factor = width /b.width.toFloat()
        return Bitmap.createScaledBitmap(b, width, (b.height * factor).toInt(), true)

    }

    //scale and maintain aspect ratio given a desired height
    //BitmapScaler.scaleToFitHeight(bitmap, 100);
    fun scaleToFitHeight(b:Bitmap, height:Int):Bitmap{
        val factor = height /b.height.toFloat()
        return Bitmap.createScaledBitmap(b, height, (b.width * factor).toInt(), true)

    }
}