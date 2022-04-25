package com.nutrikares.nutrideskapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class App: Application(){

    override fun onCreate() {
        super.onCreate()
//        val preferences = PreferenceManager.getDefaultSharedPreferences(baseContext)
//        val theme = preferences.getString("theme", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM.toString())
//        theme?.let { AppCompatDelegate.setDefaultNightMode(theme.toInt()) }
        Firebase.database.setPersistenceEnabled(true)
    }
}
