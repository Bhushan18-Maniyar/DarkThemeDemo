package com.example.darkthemedemo

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        sharedPreferences = getSharedPreferences(MainActivity.CONSTANT.fileName, MODE_PRIVATE)

        switch_darkMode.isChecked =
                sharedPreferences.getBoolean(MainActivity.CONSTANT.isDarkModeEnabled, false)

        switch_darkMode.setOnCheckedChangeListener(this)

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val nightMode = newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK

        when (nightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                switch_darkMode.isChecked = false
                toast("Light Mode is On")
            }

            Configuration.UI_MODE_NIGHT_YES -> {
                switch_darkMode.isChecked = true
                toast("Night Mode is On")
            }
        }

    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            if (isChecked) {
                AppCompatDelegate
                        .setDefaultNightMode(
                                AppCompatDelegate.MODE_NIGHT_YES
                        )
            } else {
                AppCompatDelegate
                        .setDefaultNightMode(
                                AppCompatDelegate.MODE_NIGHT_NO
                        )
            }
            editor = sharedPreferences.edit()
            editor.putBoolean(MainActivity.CONSTANT.isDarkModeEnabled, isChecked)
            editor.apply()
        }
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}