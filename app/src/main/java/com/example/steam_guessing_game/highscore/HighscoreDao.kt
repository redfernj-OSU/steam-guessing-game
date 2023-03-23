package com.example.steam_guessing_game.highscore

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HighscoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(highscoreEntity: HighscoreEntity)

    @Delete
    suspend fun delete(highscoreEntity: HighscoreEntity)

    @Query(
        "SELECT * FROM HighscoreEntity"
    )
    fun getScore(): Flow<HighscoreEntity>

}