package com.carissa.fundamental.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.carissa.fundamental.R
import com.carissa.fundamental.viewmodel.SettingPreferenceViewModel
import com.carissa.fundamental.viewmodel.SettingPreferenceViewModelFactory
import com.carissa.fundamental.viewmodel.SettingPreferences
import com.carissa.fundamental.viewmodel.dataStore

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val delayMillis: Long = 2000
        val mainIntent = Intent(this, MainActivity::class.java)

        Thread {
            Thread.sleep(delayMillis)
            startActivity(mainIntent)
            finish()
        }.start()

        val preference = SettingPreferences.getInstance(application.dataStore)
        val preferenceViewModel = ViewModelProvider(this, SettingPreferenceViewModelFactory(preference))[SettingPreferenceViewModel::class.java]
        preferenceViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}