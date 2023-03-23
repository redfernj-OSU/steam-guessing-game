package com.example.steam_guessing_game.sound

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SoundDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(soundEntity: SoundEntity)

    @Delete
    suspend fun delete(soundEntity: SoundEntity?)



//    getSounds
    @Query(
        "SELECT * FROM SoundEntity"
    )
    fun getSoundsUnfiltered(): Flow<List<SoundEntity>>

    @Query(
        "SELECT * FROM SoundEntity WHERE chosen = TRUE AND soundTrigger = :soundTrigger"
    )
    fun getSoundsFilteredChosen(soundTrigger: String): Flow<List<SoundEntity?>>

    @Query(
        "SELECT * FROM SoundEntity WHERE soundFranchise = :soundFranchise AND soundTrigger = :soundTrigger LIMIT 1"
    )
    fun getSoundsFilteredFranchise(soundFranchise: String, soundTrigger: String): Flow<SoundEntity?>



//    getSound
    @Query(
        "SELECT * FROM SoundEntity WHERE soundLabel = :soundLabel"
    )
    fun getSound(soundLabel: String): Flow<SoundEntity>


//    setChosen
    @Query(
        "UPDATE SoundEntity SET chosen = TRUE WHERE soundFranchise = :soundFranchise OR soundFranchise = 'default'"
    )
    suspend fun setChosen(soundFranchise: String)

    @Query(
        "UPDATE SoundEntity SET chosen = FALSE"
    )
    suspend fun removeChosen()

}