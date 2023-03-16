package com.example.steam_guessing_game

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SoundEntity(
    @PrimaryKey
//    URL to pull sound data
    val sound_url      : String,
//    How to trigger sound (win, lose, etc.)
    val soundTrigger     : String,
//    Franchise that the sound comes from
    val soundFranchise : String
)
