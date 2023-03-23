package com.example.steam_guessing_game.highscore

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HighscoreEntity::class], version = 1)
abstract class HighscoreDatabase : RoomDatabase() {
    abstract fun highscoreDao(): HighscoreDao

    companion object {
        @Volatile private var instance: HighscoreDatabase?= null

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                HighscoreDatabase::class.java,
                "highscore.db"
            ).build()

        fun getInstance(context: Context): HighscoreDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it

                }
            }
        }
    }
}