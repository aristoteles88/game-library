package com.aristotelesjunior.gamelibrary.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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
}