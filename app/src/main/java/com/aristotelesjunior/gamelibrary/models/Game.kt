package com.aristotelesjunior.gamelibrary.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.aristotelesjunior.gamelibrary.R
import com.aristotelesjunior.gamelibrary.database.DataConverter
import com.aristotelesjunior.gamelibrary.database.GameDB

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
    @ColumnInfo(name = "rating") val rating : Int,
    @ColumnInfo(name = "started") val started : Boolean,
    @ColumnInfo(name = "finished") val finished : Boolean,
    @ColumnInfo(name = "wishlist") val wishlist : Boolean,
    @ColumnInfo(name = "platform") val platform : String,
    @ColumnInfo(name = "cover", typeAffinity = ColumnInfo.BLOB) val image : ByteArray,
//    @ColumnInfo(name = "screenshots", typeAffinity = ColumnInfo.BLOB) val screenshots : List<ByteArray>,
)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Game

        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        return image.contentHashCode()
    }
}
