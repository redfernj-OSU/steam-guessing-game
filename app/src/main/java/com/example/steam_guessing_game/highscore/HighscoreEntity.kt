package com.example.steam_guessing_game.highscore

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HighscoreEntity(
    @PrimaryKey
    val highScore: String,

    val score: Int

)
