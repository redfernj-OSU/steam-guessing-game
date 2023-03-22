package com.example.steam_guessing_game.sound

import com.example.steam_guessing_game.sound.SoundDao
import com.example.steam_guessing_game.sound.SoundEntity

class SoundRepository(private val dao: SoundDao) {
    suspend fun insertSound(sound: SoundEntity) =
        dao.insert(sound)

    suspend fun deleteSound(sound: SoundEntity) =
        dao.delete(sound)

    fun getSoundsUnfiltered() =
        dao.getSoundsUnfiltered()

    fun getSoundsFiltered(soundFranchise: String) =
        dao.getSoundsFiltered(soundFranchise)

    fun getSound(soundLabel: String) =
        dao.getSound(soundLabel)

    suspend fun setChosen(soundFranchise: String) =
        dao.setChosen(soundFranchise)

    suspend fun removeChosen() =
        dao.removeChosen()

}