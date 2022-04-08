package com.udinn.submission2.Main

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class SettingViewMosel(private val pref: SettingPreferences) : ViewModel() {
    
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}
