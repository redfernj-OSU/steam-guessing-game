package com.example.steam_guessing_game.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.steam_guessing_game.R
import com.example.steam_guessing_game.api.SteamService
import com.example.steam_guessing_game.api.SteamStoreService
import com.example.steam_guessing_game.data.App
import com.example.steam_guessing_game.data.Review
import com.example.steam_guessing_game.data.ReviewResults
import com.example.steam_guessing_game.data.SteamQueryResults
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val steamService = SteamService.create()
    private var id = 0
    private var correctID = 0
    private var review = ""
    lateinit var apps : List<App>



    private val steamStoreService = SteamStoreService.create()
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
                        apps = response.body()!!.applist.apps
                        val temp : App = apps[Random.nextInt(apps.size)]
                        getReviews(temp.appid.toLong())
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

    private fun getReviews(id : Long){
        steamStoreService.getReviews(id)
            .enqueue(object : Callback<ReviewResults>{
                override fun onResponse(call: Call<ReviewResults>, response: Response<ReviewResults>){
                    if(response.isSuccessful){
                        if(response.body()?.success == 1 && response.body()!!.reviews.size > 0){

                            review = response.body()!!.reviews[0].review
                            correctID = id.toInt()

                            Log.d("MainActivity", "Review: ${correctID} ::: ${review}")
                        }
                        else{
                            getReviews(apps[Random.nextInt(apps.size)].appid.toLong())
                        }

                    }
                    else{
                        Log.e("f", "Error: ${response.errorBody()?.string()}")
                    }

                }

                override fun onFailure(call: Call<ReviewResults>, t: Throwable) {
                    Log.d("MainActivity", "Error making API call: ${t.message}")
                }
            })
    }
}

