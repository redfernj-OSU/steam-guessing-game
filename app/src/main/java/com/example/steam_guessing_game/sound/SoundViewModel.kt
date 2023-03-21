package com.example.steam_guessing_game.sound

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SoundViewModel(application: Application): AndroidViewModel(application) {
    private val repository = SoundRepository(
        SoundDatabase.getInstance(application).soundDao()
    )

    val sounds = repository.getSoundsUnfiltered()

    fun addSound(sound: SoundEntity) {
        viewModelScope.launch {
            repository.insertSound(sound)
        }
    }

    fun removeSound(sound: SoundEntity) {
        viewModelScope.launch {
            repository.deleteSound(sound)
        }
    }

    fun getSound(soundTrigger: String, soundFranchise: String) =
        repository.getSoundsFiltered(soundTrigger, soundFranchise)

    fun getSound(label: String) =
        repository.getSound(label).asLiveData()
}