package com.example

import android.app.Application
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.PasswordRepository

class PasswordApplication : Application() {
    lateinit var database: AppDatabase
        private set
        
    lateinit var repository: PasswordRepository
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "password_database"
        ).build()
        repository = PasswordRepository(database.passwordDao())
    }
}
