package com.example.steam_guessing_game.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.steam_guessing_game.R
import com.example.steam_guessing_game.sound.SoundEntity
import com.example.steam_guessing_game.sound.SoundViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfig: AppBarConfiguration
    private val soundViewModel: SoundViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfig = AppBarConfiguration(navController.graph)
        supportActionBar?.hide()
        //setupActionBarWithNavController(navController, appBarConfig)

        val defaultOnWin = SoundEntity(
            "default_on_win",
            "https://wiki.teamfortress.com/w/images/d/d6/Sf12_bcon_held_up10.wav",
            "On Win",
            "default",
        true
        )

        val defaultOnLose = SoundEntity(
            "default_on_lose",
            "https://wiki.teamfortress.com/w/images/b/be/Farmer_lose03.wav",
            "On Lose",
            "default",
            true
        )

        val defaultOnClick = SoundEntity(
            "default_on_click",
            "https://wiki.teamfortress.com/w/images/c/cf/Hitsound.wav",
            "On Click",
            "default",
            true
        )

        val defaultShowGame = SoundEntity(
            "default_show_game",
            "https://wiki.teamfortress.com/w/images/f/f6/Farmer_roundstart04.wav",
            "Show Game",
            "default",
            true
        )
        soundViewModel.addSound(defaultOnWin)
        soundViewModel.addSound(defaultOnLose)
        soundViewModel.addSound(defaultOnClick)
        soundViewModel.addSound(defaultShowGame)

//        findViewById<NavigationView>(R.id.)?.setupWithNavController(navController)
    }
}