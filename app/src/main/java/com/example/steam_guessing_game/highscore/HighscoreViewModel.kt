package com.example.steam_guessing_game.highscore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HighscoreViewModel(application: Application): AndroidViewModel(application) {
    private val repository = HighscoreRepository(
        HighscoreDatabase.getInstance(application).highscoreDao()
    )

    var highscores = getScore()


    fun addScore(highScore: HighscoreEntity) {
        viewModelScope.launch {
            repository.insertScore(highScore)
        }
    }

    fun getScore() =
        repository.getScore().asLiveData()
}