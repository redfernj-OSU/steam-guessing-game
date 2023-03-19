package com.example.steam_guessing_game.api

import com.example.steam_guessing_game.data.SteamQueryResults
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface SteamService {
    @GET("ISteamApps/GetAppList/v2/")
    fun getQueries() : Call<SteamQueryResults>

    companion object {
        private const val BASE_URL = "https://api.steampowered.com/"

        fun create() : SteamService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(SteamService::class.java)
        }
    }
}