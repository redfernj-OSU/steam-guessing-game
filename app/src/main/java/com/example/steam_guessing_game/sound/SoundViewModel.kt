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

    fun setChosen(soundFranchise: String) {
        viewModelScope.launch {
            repository.setChosen(soundFranchise)
        }
    }

    fun removeChosen() {
        viewModelScope.launch {
            repository.removeChosen()
        }
    }

    fun getSounds(soundFranchise: String, soundTrigger: String) =
        repository.getSoundsFiltered(soundFranchise, soundTrigger).asLiveData()

    fun getSounds() =
        repository.getSoundsUnfiltered().asLiveData()
}