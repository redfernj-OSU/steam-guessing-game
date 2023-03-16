package com.example.steam_guessing_game

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SoundDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(soundEntity: SoundEntity)

    @Delete
    suspend fun delete(soundEntity: SoundEntity)

//    Get all sounds with a specific sound_trigger and sound_franchise
    @Query(
        "SELECT * FROM SoundEntity WHERE sound_trigger = :sound_trigger AND sound_franchise = :sound_franchise"
    )
    fun getSound(sound_trigger: String, sound_franchise: String): Flow<List<SoundEntity?>>
}