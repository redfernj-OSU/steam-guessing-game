package com.example.steam_guessing_game.ui

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.steam_guessing_game.R
import com.example.steam_guessing_game.api.SteamService
import com.example.steam_guessing_game.api.SteamStoreService
import com.example.steam_guessing_game.data.App
import com.example.steam_guessing_game.data.ReviewResults
import com.example.steam_guessing_game.data.SteamQueryResults
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val steamService = SteamService.create()
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
                        getReviews(apps[Random.nextInt(apps.size)].appid.toLong())

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
                            val imageView = findViewById<ImageView>(R.id.imageView3)


                            if(placeImage(imageView, correctID.toLong(), true)){
                                var temp = false
                                while(!temp){
                                    temp = placeImage(findViewById<ImageView>(R.id.imageView4),1641950, false)
                                }
                                temp = false
                                while(!temp){
                                    temp = placeImage(findViewById<ImageView>(R.id.imageView5),apps[Random.nextInt(apps.size)].appid.toLong(), false)
                                }
                                temp = false
                                while(!temp){
                                    temp = placeImage(findViewById<ImageView>(R.id.imageView6),apps[Random.nextInt(apps.size)].appid.toLong(), false)
                                }
                                Log.d("MainActivity", "Review: ${correctID} ::: ${review}")
                            }
                            else{
                                getReviews(apps[Random.nextInt(apps.size)].appid.toLong())
                            }
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

    private fun placeImage(view: ImageView, id: Long, isMain: Boolean) : Boolean{
        val url = "https://cdn.cloudflare.steamstatic.com/steam/apps/${id}/header.jpg"
        val handler = Handler(Looper.getMainLooper())
        if(isUrlValid(url)){
            Glide.with(baseContext)
                .load(url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        handler.post{
                            placeImage(view,apps[Random.nextInt(apps.size)].appid.toLong(), false)
                        }
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        if(isMain == true) {
                            if(correctID != id.toInt()){
                                correctID = id.toInt()
                                handler.post{
                                    getReviews(id)
                                }
                            }

                        }
                        return false
                    }
                })
                .into(view)
            return true
        }
        return false

    }

    fun isUrlValid(urlString: String): Boolean {
        return try {
            URL(urlString).toURI()
            true
        } catch (e: Exception) {
            false
        }
    }
}

