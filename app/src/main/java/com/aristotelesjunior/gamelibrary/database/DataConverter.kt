package com.aristotelesjunior.gamelibrary.database

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream


class DataConverter {

    @TypeConverter
    fun fromGenreList(value: List<String>): String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toGenreList(value: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromScreenshotList(value: List<ByteArray>): String {
        val gson = Gson()
        val type = object : TypeToken<List<ByteArray>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toScreenshotList(value: String): List<ByteArray> {
        val gson = Gson()
        val type = object : TypeToken<List<ByteArray>>() {}.type
        return gson.fromJson(value, type)
    }

    object DbBitmapUtility {
        // convert from bitmap to byte array
        fun getBytes(bitmap: Bitmap): ByteArray {
            val stream = ByteArrayOutputStream()
            bitmap.compress(CompressFormat.PNG, 0, stream)
            return stream.toByteArray()
        }

        // convert from byte array to bitmap
        fun getImage(image: ByteArray): Bitmap {
            return BitmapFactory.decodeByteArray(image, 0, image.size)
        }
    }
}