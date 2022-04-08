package com.udinn.submission2.Main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.udinn.submission2.Main.favorite.FavoriteActivity
import com.udinn.submission2.Main.notification.NotificationActivity
import com.udinn.submission2.databinding.ActivitySettingBinding


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var viewModel: SettingViewMosel

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingViewMosel::class.java
        )

        viewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(SettingViewMosel::class.java)

        settingViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    binding.Dark.visibility = View.VISIBLE
                    binding.textDark.visibility = View.VISIBLE
                    binding.Sun.visibility = View.GONE
                    binding.textLight.visibility = View.GONE
                    binding.switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    binding.Sun.visibility = View.VISIBLE
                    binding.textLight.visibility = View.VISIBLE
                    binding.Dark.visibility = View.GONE
                    binding.textDark.visibility = View.GONE
                    binding.switchTheme.isChecked = false
                }
            })

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }
        binding.schedule.setOnClickListener {
            Intent(this@SettingActivity, NotificationActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.favorite.setOnClickListener {
            Intent(this@SettingActivity, FavoriteActivity::class.java).also {
                startActivity(it)
            }
        }


        }
    }
