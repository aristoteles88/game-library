package com.aristotelesjunior.gamelibrary.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.aristotelesjunior.gamelibrary.models.Platform

@Dao
interface PlatformDao {

    @Query("SELECT * FROM platform")
    fun getPlatforms() : List<Platform>

    @Insert
    fun insertPlatform(platform: Platform)

    @Update
    fun updatePlatform(platform: Platform)

    @Delete
    fun deletePlatform(platform: Platform)
}