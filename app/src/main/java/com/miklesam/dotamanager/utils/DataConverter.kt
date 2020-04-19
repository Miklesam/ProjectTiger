package com.miklesam.dotamanager.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

class DataConverter {
    companion object{
        fun convertImage2ByteArray(bitmap: Bitmap): ByteArray{
            val stream= ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG,0,stream)
            return stream.toByteArray()
        }

        fun convertByteArray2Image(byteArray: ByteArray): Bitmap{
            return BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
        }


    }
}