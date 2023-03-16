package com.example.steam_guessing_game

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SoundEntity::class], version = 1)
abstract class SoundDatabase : RoomDatabase() {
    abstract fun soundDao(): SoundDao

    companion object {
        @Volatile private var instance: SoundDatabase ?= null

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                SoundDatabase::class.java,
                "availableSounds.db"
            ).build()

        fun getInstance(context: Context): SoundDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }
    }
}