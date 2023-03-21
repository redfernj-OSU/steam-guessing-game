package com.example.steam_guessing_game.fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.steam_guessing_game.R
import com.example.steam_guessing_game.sound.SoundEntity
import com.example.steam_guessing_game.sound.SoundRepository
import com.example.steam_guessing_game.sound.SoundViewModel

class CreateSoundFragment: Fragment(R.layout.create_sound) {

    private val soundViewModel: SoundViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = view.findViewById(R.id.sound_trigger_spinner)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sound_trigger_list,
            android.R.layout.simple_spinner_item
        ).also { adaper ->
            adaper.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adaper
        }

        view.findViewById<Button>(R.id.create_sound_button).setOnClickListener {
            var newSound = SoundEntity(
                R.id.sound_label_input.toString(),
                R.id.sound_URL_input.toString(),
                R.id.sound_trigger_spinner.toString(),
                R.id.sound_Franchise_input.toString()
            )

            soundViewModel.addSound(newSound)
        }
    }
}