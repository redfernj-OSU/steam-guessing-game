package com.example.steam_guessing_game.fragment

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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
                if (!soundLabels.contains(i.soundFranchise)) {
                    soundLabels.add(i.soundFranchise)
                }
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

        view.findViewById<Button>(R.id.set_sound_button).setOnClickListener {
            soundViewModel.removeChosen()
            soundViewModel.setChosen(
                view.findViewById<Spinner>(R.id.sound_available_spinner).getSelectedItem().toString()
            )
            findNavController().navigate(R.id.menu_fragment)
        }

        var mediaPlayer = MediaPlayer()
        view.findViewById<Button>(R.id.test_on_win).setOnClickListener {

            soundViewModel.getSounds(
                view.findViewById<Spinner>(R.id.sound_available_spinner).getSelectedItem().toString(),
                "On Win"
                ).observe(viewLifecycleOwner) {sound ->
                try {
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(sound?.soundURL)
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }

        view.findViewById<Button>(R.id.test_on_click).setOnClickListener {

            soundViewModel.getSounds(
                view.findViewById<Spinner>(R.id.sound_available_spinner).getSelectedItem().toString(),
                "On Click"
            ).observe(viewLifecycleOwner) {sound ->
                try {
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(sound?.soundURL)
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }

        view.findViewById<Button>(R.id.test_on_lose).setOnClickListener {

            soundViewModel.getSounds(
                view.findViewById<Spinner>(R.id.sound_available_spinner).getSelectedItem().toString(),
                "On Lose"
            ).observe(viewLifecycleOwner) {sound ->
                try {
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(sound?.soundURL)
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }

        view.findViewById<Button>(R.id.test_show_game).setOnClickListener {

            soundViewModel.getSounds(
                view.findViewById<Spinner>(R.id.sound_available_spinner).getSelectedItem().toString(),
                "Show Game"
            ).observe(viewLifecycleOwner) {sound ->
                try {
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(sound?.soundURL)
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }

        view.findViewById<Button>(R.id.main_menu_button_sound).setOnClickListener{
            findNavController().navigate(R.id.menu_fragment)
        }
    }
}
