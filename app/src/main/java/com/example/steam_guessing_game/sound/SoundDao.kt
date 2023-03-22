package com.example.steam_guessing_game.sound

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SoundDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(soundEntity: SoundEntity)

    @Delete
    suspend fun delete(soundEntity: SoundEntity?)

    @Query(
        "SELECT * FROM SoundEntity"
    )
    fun getSoundsUnfiltered(): Flow<List<SoundEntity>>

//    Get all sounds with a specific sound_trigger and sound_franchise
    @Query(
        "SELECT * FROM SoundEntity WHERE soundTrigger = :soundTrigger AND soundFranchise = :soundFranchise"
    )
    fun getSoundsFiltered(soundTrigger: String, soundFranchise: String): Flow<List<SoundEntity?>>

    @Query(
        "SELECT * FROM SoundEntity Where soundLabel = :soundLabel"
    )
    fun getSound(soundLabel: String): Flow<SoundEntity>
}