package com.hera.restcountries.ui

import androidx.lifecycle.ViewModel
import com.hera.restcountries.data.model.Country
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

    var allCountries: List<Country>? = null
    val allCountriesFlow = repository.getAllCountries()

    val query = MutableStateFlow("")
    var countriesByName: List<Country>? = null
    val countriesByNameFlow = query.flatMapLatest { query ->
        repository.getCountriesByName(query)
    }

    var theme = THEME_LIGHT
}