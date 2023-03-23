package com.example.steam_guessing_game.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.steam_guessing_game.R

class MenuFragment: Fragment(R.layout.menu) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.start_game_button).setOnClickListener {
            val action = MenuFragmentDirections.navigateToGame(0)
            findNavController().navigate(action)
        }

        view.findViewById<Button>(R.id.start_create_sound_button).setOnClickListener {
            findNavController().navigate(R.id.create_sound_fragment)
        }
    }
}