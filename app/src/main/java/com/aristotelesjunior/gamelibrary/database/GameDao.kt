package com.aristotelesjunior.gamelibrary.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface GameDao {

    @Query("SELECT * FROM game")
    fun getGames() : List<Game>

    @Insert
    fun insertGame(game: Game)

    @Update
    fun updateGame(game: Game)

    @Delete
    fun deleteGame(platform: Game)
}