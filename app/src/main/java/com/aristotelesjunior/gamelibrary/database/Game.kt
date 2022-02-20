package com.aristotelesjunior.gamelibrary.database

import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(
        entity = Platform::class,
        parentColumns = ["id"],
        childColumns = ["platform"],
        onDelete = ForeignKey.CASCADE,
    )])
data class Game (
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "releaseDate") val releaseDate : String,
    @ColumnInfo(name = "description") val description : String,
    @ColumnInfo(name = "genre") val genre : String,
    @ColumnInfo(name = "score") val score : Int,
    @ColumnInfo(name = "finished") val finished : Boolean,
    @ColumnInfo(name = "platform") val platform : String,
//    @ColumnInfo(name = "cover", typeAffinity = ColumnInfo.BLOB) val image : ByteArray,
//    @ColumnInfo(name = "screenshots", typeAffinity = ColumnInfo.BLOB) val screenshots : List<ByteArray>,
)
//{
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as Game
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
