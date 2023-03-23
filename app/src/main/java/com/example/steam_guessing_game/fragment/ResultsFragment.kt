package com.example.steam_guessing_game.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import com.example.steam_guessing_game.R
import com.google.android.material.navigation.NavigationView

class ResultsFragment: Fragment(R.layout.results) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val correctIndex = requireArguments().getInt("randomIndex")
        val selectedIndex = requireArguments().getInt("selectedIndex")
        val correctID = requireArguments().getInt("correctID")
        var curScore = requireArguments().getInt("currentScore")

        val continueButton = view.findViewById<Button>(R.id.continue_button)
        val menuButton = view.findViewById<Button>(R.id.return_to_menu_button)
        val steamButton = view.findViewById<Button>(R.id.steam_page_button)




        Log.d("Results", "${correctID}")

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
            Log.d("Score", curScore.toString())
            findNavController().navigate(R.id.menu_fragment)
        }

    }

}