package com.hera.restcountries.ui

import androidx.lifecycle.ViewModel
import com.hera.restcountries.data.repository.Repository
import com.hera.restcountries.util.Constants.THEME_LIGHT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val allCountries = repository.getAllCountries()

    val query = MutableStateFlow("")
    val countriesByName = query.flatMapLatest { query ->
        repository.getCountriesByName(query)
    }

    var theme = THEME_LIGHT
}