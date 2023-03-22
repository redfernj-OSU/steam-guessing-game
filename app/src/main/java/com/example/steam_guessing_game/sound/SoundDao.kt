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
        "SELECT * FROM SoundEntity WHERE soundFranchise = :soundFranchise"
    )
    fun getSoundsFiltered(soundFranchise: String): Flow<List<SoundEntity?>>

    @Query(
        "SELECT * FROM SoundEntity WHERE soundLabel = :soundLabel"
    )
    fun getSound(soundLabel: String): Flow<SoundEntity>

    @Query(
        "UPDATE SoundEntity SET chosen = TRUE WHERE soundFranchise = :soundFranchise"
    )
    suspend fun setChosen(soundFranchise: String)

    @Query(
        "UPDATE SoundEntity SET chosen = FALSE"
    )
    suspend fun removeChosen()

}