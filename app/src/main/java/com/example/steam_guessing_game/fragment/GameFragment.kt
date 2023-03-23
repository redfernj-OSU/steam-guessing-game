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
import android.widget.Button
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
    lateinit var ImageView3 : ImageView
    lateinit var ImageView4 : ImageView
    lateinit var ImageView5 : ImageView
    lateinit var ImageView6 : ImageView



    private val steamStoreService = SteamStoreService.create()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val curScore = requireArguments().getInt("currentScore")
        super.onViewCreated(view, savedInstanceState)

        val nums = listOf(R.id.imageView3, R.id.imageView4, R.id.imageView5, R.id.imageView6)
        val randomIndex = nums.shuffled()

        getSteamApps(randomIndex)

        ImageView3 = view.findViewById<ImageView>(R.id.imageView3)
        ImageView4 = view.findViewById<ImageView>(R.id.imageView4)
        ImageView5 = view.findViewById<ImageView>(R.id.imageView5)
        ImageView6 = view.findViewById<ImageView>(R.id.imageView6)
        val scoreText = view.findViewById<TextView>(R.id.scoreText)
        scoreText.text = "Score: ${curScore}"

        ImageView3.isEnabled = false
        ImageView4.isEnabled = false
        ImageView5.isEnabled = false
        ImageView6.isEnabled = false


        ImageView3.setOnClickListener {
            val action = GameFragmentDirections.navigateToResults(randomIndex[0], R.id.imageView3, correctID, curScore)
            findNavController().navigate(action)
        }
        ImageView4.setOnClickListener {
            val action = GameFragmentDirections.navigateToResults(randomIndex[0], R.id.imageView4, correctID, curScore)
            findNavController().navigate(action)
        }
        ImageView5.setOnClickListener {
            val action = GameFragmentDirections.navigateToResults(randomIndex[0], R.id.imageView5, correctID, curScore)
            findNavController().navigate(action)
        }
        ImageView6.setOnClickListener {
            val action = GameFragmentDirections.navigateToResults(randomIndex[0], R.id.imageView6, correctID, curScore)
            findNavController().navigate(action)
        }
    }

    private fun getSteamApps(randomIndex: List<Int>){
        steamService.getQueries()
            .enqueue(object : Callback<SteamQueryResults> {
                override fun onResponse(call: Call<SteamQueryResults>, response: Response<SteamQueryResults>){
                    if(response.isSuccessful){
                        apps = response.body()!!.applist.apps
                        getReviews(apps[Random.nextInt(apps.size)].appid.toLong(), randomIndex)

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

    private fun getReviews(id : Long, randomIndex : List<Int>){
        steamStoreService.getReviews(id)
            .enqueue(object : Callback<ReviewResults>{
                override fun onResponse(call: Call<ReviewResults>, response: Response<ReviewResults>){
                    if(response.isSuccessful){
                        if(response.body()?.success == 1 && response.body()!!.reviews.size > 0){

                            review = response.body()!!.reviews[0].review
                            view!!.findViewById<TextView>(R.id.textViewReview).text = review
                            correctID = id.toInt()


                            val imageView = view!!.findViewById<ImageView>(randomIndex[0])


                            if(placeImage(imageView, correctID.toLong(), true, randomIndex)){
                                var temp = false
                                while(!temp){
                                    temp = placeImage(view!!.findViewById<ImageView>(randomIndex[1]),apps[Random.nextInt(apps.size)].appid.toLong(), false, randomIndex)
                                }
                                temp = false
                                while(!temp){
                                    temp = placeImage(view!!.findViewById<ImageView>(randomIndex[2]),apps[Random.nextInt(apps.size)].appid.toLong(), false, randomIndex)
                                }
                                temp = false
                                while(!temp){
                                    temp = placeImage(view!!.findViewById<ImageView>(randomIndex[3]),apps[Random.nextInt(apps.size)].appid.toLong(), false, randomIndex)
                                }
                                Log.d("Review", "Review: ${correctID} ::: ${review}")
                            }
                            else{
                                getReviews(apps[Random.nextInt(apps.size)].appid.toLong(), randomIndex)
                            }
                        }
                        else{
                            getReviews(apps[Random.nextInt(apps.size)].appid.toLong(), randomIndex)
                        }

                        ImageView3.isEnabled = true
                        ImageView4.isEnabled = true
                        ImageView5.isEnabled = true
                        ImageView6.isEnabled = true

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

    private fun placeImage(view: ImageView, id: Long, isMain: Boolean, randomIndex: List<Int>) : Boolean{
        val url = "https://cdn.cloudflare.steamstatic.com/steam/apps/${id}/capsule_231x87.jpg"
        view.tag = url
        val handler = Handler(Looper.getMainLooper())
        if(isUrlValid(url)){
            Glide.with(requireContext())
                .load(url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        handler.post{
                            placeImage(view,apps[Random.nextInt(apps.size)].appid.toLong(), false, randomIndex)
                        }
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        if(isMain == true) {
                            if(correctID != id.toInt()){
                                correctID = id.toInt()
                                handler.post{
                                    getReviews(id, randomIndex)
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