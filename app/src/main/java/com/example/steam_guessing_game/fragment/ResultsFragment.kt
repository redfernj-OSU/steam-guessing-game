package com.example.steam_guessing_game.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.steam_guessing_game.R
import com.google.android.material.navigation.NavigationView

class ResultsFragment: Fragment(R.layout.results) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val correctIndex = requireArguments().getInt("randomIndex")
        val selectedIndex = requireArguments().getInt("selectedIndex")
        val correctID = requireArguments().getInt("correctID")

        Log.d("Results", "${correctID}")

        if(correctIndex == selectedIndex){
            //win condition
        }
        else{
            //lose condition
        }
    }

}