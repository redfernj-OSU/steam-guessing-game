package com.example.steam_guessing_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(
//                R.id.nav_host_fragment
//            ) as NavHostFragment
//
//        val navController = navHostFragment.navController
//        val navigationView: NavigationView = findViewById(R.id.nav_view)
//        navigationView?.setupWithNavController(navController)
    }
}