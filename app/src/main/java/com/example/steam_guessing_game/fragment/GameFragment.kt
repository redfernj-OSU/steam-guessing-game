package com.example.steam_guessing_game.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL
import kotlin.random.Random

class GameFragment: Fragment(R.layout.game) {
    private val steamService = SteamService.create()
    private var correctID = 0
    private var review = ""
    lateinit var apps : List<App>

    private val steamStoreService = SteamStoreService.create()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        getSteamApps()
    }

    private fun getSteamApps(){
        steamService.getQueries()
            .enqueue(object : Callback<SteamQueryResults> {
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
                            view!!.findViewById<TextView>(R.id.textViewReview).text = review
                            correctID = id.toInt()
                            val imageView = view!!.findViewById<ImageView>(R.id.imageView3)


                            if(placeImage(imageView, correctID.toLong(), true)){
                                var temp = false
                                while(!temp){
                                    temp = placeImage(view!!.findViewById<ImageView>(R.id.imageView4),1641950, false)
                                }
                                temp = false
                                while(!temp){
                                    temp = placeImage(view!!.findViewById<ImageView>(R.id.imageView5),apps[Random.nextInt(apps.size)].appid.toLong(), false)
                                }
                                temp = false
                                while(!temp){
                                    temp = placeImage(view!!.findViewById<ImageView>(R.id.imageView6),apps[Random.nextInt(apps.size)].appid.toLong(), false)
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
        val url = "https://cdn.cloudflare.steamstatic.com/steam/apps/${id}/capsule_231x87.jpg"
        val handler = Handler(Looper.getMainLooper())
        if(isUrlValid(url)){
            Glide.with(requireContext())
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.create_sound_menu -> {
                findNavController().navigate(R.id.create_sound_fragment)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}