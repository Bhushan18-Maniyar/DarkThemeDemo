package com.example.darkthemedemo

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    object CONSTANT {
        const val fileName = "com.example.darkthemedemo.UserSettings"
        const val isDarkModeEnabled = "isDarkModeEnabled"
    }

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var isDarkMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences =
                getSharedPreferences(CONSTANT.fileName, MODE_PRIVATE)

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            isDarkMode = if (sharedPreferences.getBoolean(CONSTANT.isDarkModeEnabled, false)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                false
            }
        }

        setContentView(R.layout.activity_main)

        fab_theme.setOnClickListener(::changeTheme)

        btn_dashboard.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        updateUi()


    }

    private fun changeTheme(view: View?) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            isDarkMode = if (isDarkMode) {
                AppCompatDelegate
                        .setDefaultNightMode(
                                AppCompatDelegate.MODE_NIGHT_NO
                        )
                false
            } else {
                AppCompatDelegate
                        .setDefaultNightMode(
                                AppCompatDelegate.MODE_NIGHT_YES
                        )
                true
            }
            editor = sharedPreferences.edit()
            editor.putBoolean(CONSTANT.isDarkModeEnabled, isDarkMode)
            editor.apply()
        } else {
            isDarkMode = !isDarkMode
        }
        updateUi()
    }

    private fun updateUi() {
        if (isDarkMode) {
            fab_theme.setImageResource(R.drawable.ic_night_vector)
        } else {
            fab_theme.setImageResource(R.drawable.ic_day_vector)
        }
    }
}