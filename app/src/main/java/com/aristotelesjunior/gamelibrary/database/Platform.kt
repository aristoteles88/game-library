package com.aristotelesjunior.gamelibrary.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Platform(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "releaseDate") val releaseDate: String,
    @ColumnInfo(name = "image") val image: Int,
)
//{
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as Platform
//
//        if (!image.contentEquals(other.image)) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        return image.contentHashCode()
//    }
//}