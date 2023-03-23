package com.example.steam_guessing_game.fragment

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.steam_guessing_game.R
import com.example.steam_guessing_game.highscore.HighscoreEntity
import com.example.steam_guessing_game.highscore.HighscoreViewModel
import com.google.android.material.navigation.NavigationView
import kotlin.random.Random

class ResultsFragment: Fragment(R.layout.results) {

    private val hsview : HighscoreViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val correctIndex = requireArguments().getInt("randomIndex")
        val selectedIndex = requireArguments().getInt("selectedIndex")
        val correctID = requireArguments().getInt("correctID")
        var curScore = requireArguments().getInt("currentScore")

        val continueButton = view.findViewById<Button>(R.id.continue_button)
        val menuButton = view.findViewById<Button>(R.id.return_to_menu_button)
        val steamButton = view.findViewById<Button>(R.id.steam_page_button)

        val resultImage = view.findViewById<ImageView>(R.id.picked_image)
        placeImage(resultImage, correctID.toLong())

        hsview.getScore().observe(viewLifecycleOwner){highscore->
            if(curScore > highscore.score){
                hsview.addScore(HighscoreEntity("Highscore", curScore))
            }
        }

        if(correctIndex == selectedIndex){
            view.findViewById<TextView>(R.id.win_text).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.lose_text).visibility = View.INVISIBLE
            continueButton.visibility = View.VISIBLE
            curScore += 1
            //win condition
        }
        else{
            view.findViewById<TextView>(R.id.lose_text).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.win_text).visibility = View.INVISIBLE
            continueButton.visibility = View.INVISIBLE
            curScore = 0
            //lose condition
        }
        steamButton.setOnClickListener{
            val url = "https://store.steampowered.com/app/${correctID}/"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        continueButton.setOnClickListener{
            Log.d("Score", curScore.toString())
            val action = ResultsFragmentDirections.navigateToGame(curScore)
            findNavController().navigate(action)
        }
        menuButton.setOnClickListener{
            findNavController().navigate(R.id.menu_fragment)
        }

    }
    private fun placeImage(view: ImageView, id: Long){
        val url = "https://cdn.cloudflare.steamstatic.com/steam/apps/${id}/capsule_231x87.jpg"

        Glide.with(requireContext())
            .load(url)
            .into(view)
    }
}