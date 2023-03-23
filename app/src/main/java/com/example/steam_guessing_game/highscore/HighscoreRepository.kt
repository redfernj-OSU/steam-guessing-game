package com.example.steam_guessing_game.highscore


class HighscoreRepository(private val dao: HighscoreDao) {
    suspend fun insertScore(Score: HighscoreEntity) = dao.insert(Score)

    fun getScore() = dao.getScore()

}