package com.example.steam_guessing_game.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.steam_guessing_game.R
import com.example.steam_guessing_game.api.SteamService
import com.example.steam_guessing_game.data.SteamQueryResults
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MainActivity : AppCompatActivity() {

    private val steamService = SteamService.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSteamApps()

    }

    private fun getSteamApps(){
        steamService.getQueries()
            .enqueue(object : Callback<SteamQueryResults>{
                override fun onResponse(call: Call<SteamQueryResults>, response: Response<SteamQueryResults>){
                    if(response.isSuccessful){
                        Log.d("MainActivity", "Search results:${response.body()?.applist?.apps?.size}")
                    }
                    else{
                        Log.e("f", "Error: ${response.errorBody()?.string()}")
                    }

                }

                override fun onFailure(call: Call<SteamQueryResults>, t: Throwable) {
                    Log.d("MainActivity", "Error making API call: ${t.message}")
                }
            })
    }
}

