package com.example.steam_guessing_game

class SoundRepository(private val dao: SoundDao) {
    suspend fun insertSound(sound: SoundEntity) =
        dao.insert(sound)

    suspend fun deleteSound(sound: SoundEntity) =
        dao.delete(sound)

    suspend fun getSounds(soundTrigger: String, soundFranchise: String) =
        dao.getSounds(soundTrigger, soundFranchise)
}