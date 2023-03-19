package com.example.steam_guessing_game.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SteamQueryResults (
    val applist: AppList
)