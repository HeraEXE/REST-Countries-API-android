package com.hera.restcountries.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.hera.restcountries.util.Constants.SETTINGS

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(SETTINGS)