package com.example.steam_guessing_game.fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.steam_guessing_game.R
import com.example.steam_guessing_game.sound.SoundEntity
import com.example.steam_guessing_game.sound.SoundRepository
import com.example.steam_guessing_game.sound.SoundViewModel

class CreateSoundFragment: Fragment(R.layout.create_sound) {

    private val soundViewModel: SoundViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val triggerSpinner: Spinner = view.findViewById(R.id.sound_trigger_spinner)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sound_trigger_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            triggerSpinner.adapter = adapter
        }

        val availableSpinner: Spinner = view.findViewById(R.id.sound_available_spinner)
        soundViewModel.getSounds().observe(viewLifecycleOwner) {sounds ->
            val soundLabels: ArrayList<String> = arrayListOf()
            for (i in sounds) {
                soundLabels.add(i.soundFranchise)
            }

            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                soundLabels
            )
            availableSpinner.adapter = adapter
        }

        view.findViewById<Button>(R.id.create_sound_button).setOnClickListener {
            var newSound = SoundEntity(
                view.findViewById<EditText>(R.id.sound_label_input).getText().toString(),
                view.findViewById<EditText>(R.id.sound_URL_input).getText().toString(),
                view.findViewById<Spinner>(R.id.sound_trigger_spinner).getSelectedItem().toString(),
                view.findViewById<EditText>(R.id.sound_Franchise_input).getText().toString()
            )

            soundViewModel.addSound(newSound)
        }
    }
}
