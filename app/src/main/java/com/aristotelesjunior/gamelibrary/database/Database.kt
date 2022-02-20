package com.aristotelesjunior.gamelibrary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Platform::class, Game::class], version = 1)
//@TypeConverters(DataConverter::class)
abstract class GamesDatabase : RoomDatabase() {
    abstract fun platformDao() : PlatformDao
    abstract fun gameDao() : GameDao
}

object GameDB {
    fun getInstance(context: Context) : GamesDatabase =
        Room.databaseBuilder(
            context,
            GamesDatabase::class.java,
            "games-database"
        ).allowMainThreadQueries().build()
}