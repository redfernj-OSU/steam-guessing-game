package com.example.steam_guessing_game.fragment

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.steam_guessing_game.R
import com.example.steam_guessing_game.highscore.HighscoreEntity
import com.example.steam_guessing_game.highscore.HighscoreViewModel
import com.example.steam_guessing_game.sound.SoundViewModel

class MenuFragment: Fragment(R.layout.menu) {
    private val hsview : HighscoreViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        hsview.highscores.observe(viewLifecycleOwner) { highscores ->
            if (highscores == null) {
                hsview.addScore(HighscoreEntity("Highscore", 0))
                view.findViewById<TextView>(R.id.MenuHighScore).text = "High Score: ${0}"
            }
            else{
                hsview.getScore().observe(viewLifecycleOwner){highscore->
                    view.findViewById<TextView>(R.id.MenuHighScore).text = "High Score: ${highscore.score}"
                }
            }
        }

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