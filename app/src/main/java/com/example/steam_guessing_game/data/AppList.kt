package com.example.steam_guessing_game.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AppList (
    val apps: List<App>
) : java.io.Serializable

@JsonClass(generateAdapter = true)
data class App(
    val appid: Int,
    val name: String
) : java.io.Serializable