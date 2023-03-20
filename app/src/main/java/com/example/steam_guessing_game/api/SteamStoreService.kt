package com.example.steam_guessing_game.api

import com.example.steam_guessing_game.data.ReviewResults
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface SteamStoreService {
    @GET("appreviews/{id}?json=1")
    fun getReviews(
        @Path("id") id: Long
    ) : Call<ReviewResults>

    companion object {
        private const val BASE_URL = "https://store.steampowered.com/"

        fun create() : SteamStoreService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(SteamStoreService::class.java)
        }
    }
}