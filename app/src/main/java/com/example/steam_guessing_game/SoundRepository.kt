package com.example.steam_guessing_game

class SoundRepository(private val dao: SoundDao) {
    suspend fun insertSound(sound: SoundEntity) =
        dao.insert(sound)

    suspend fun deleteSound(sound: SoundEntity) =
        dao.delete(sound)

    fun getSoundsUnfiltered() =
        dao.getSoundsUnfiltered()

    fun getSoundsFiltered(soundTrigger: String, soundFranchise: String) =
        dao.getSoundsFiltered(soundTrigger, soundFranchise)

    fun getSound(soundLabel: String) =
        dao.getSound(soundLabel)


}