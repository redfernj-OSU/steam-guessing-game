package com.example.steam_guessing_game.sound

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SoundEntity(
    val soundLabel: String,

//    URL to pull sound data
    val soundURL: String,
//    How to trigger sound (win, lose, etc.)
    val soundTrigger: String,
//    Franchise that the sound comes from
    @PrimaryKey
    val soundFranchise: String,

    var chosen: Boolean = false
)
