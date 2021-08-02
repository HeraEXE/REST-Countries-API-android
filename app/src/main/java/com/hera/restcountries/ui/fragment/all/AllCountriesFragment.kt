package com.hera.restcountries.ui.fragment.all

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hera.restcountries.R
import com.hera.restcountries.data.model.Country
import com.hera.restcountries.databinding.FragmentAllCountriesBinding
import com.hera.restcountries.ui.MainActivity
import com.hera.restcountries.ui.MainViewModel
import com.hera.restcountries.ui.adapter.CountriesAdapter
import com.hera.restcountries.util.Constants.THEME_DARK
import com.hera.restcountries.util.Constants.THEME_KEY
import com.hera.restcountries.util.Constants.THEME_LIGHT
import com.hera.restcountries.util.Resource
import com.hera.restcountries.util.dataStore
import com.hera.restcountries.util.extension.setOnQueryListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.w3c.dom.Text

@AndroidEntryPoint
class
AllCountriesFragment : Fragment(R.layout.fragment_all_countries), CountriesAdapter.Listener {

    private lateinit var viewModel: MainViewModel
    private val countriesAdapter = CountriesAdapter(this)
    private var _binding: FragmentAllCountriesBinding? = null
    private val binding get() = _binding!!
    private val themeKey = stringPreferencesKey(THEME_KEY)
    private lateinit var themeFlow: Flow<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).mainViewModel
        setHasOptionsMenu(true)
        setPreferencesFlow()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPreferencesData()
        initializeViews(view)
        if (countriesAdapter.differ.currentList.isEmpty())
            observeCountries()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_all, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        setSearchView(searchView)
    }


    override fun onOptionsItemSelected(item: MenuItem) = if (item.itemId == R.id.action_theme) {
        setPreferencesData()
        getPreferencesData()
        true
    } else {
        super.onOptionsItemSelected(item)
    }


    private fun setSearchView(searchView: SearchView) {
        searchView.queryHint = "Search..."
        searchView.setOnQueryListener { query ->
            val action = AllCountriesFragmentDirections.actionAllCountriesFragmentToSearchFragment(query)
            findNavController().navigate(action)
        }
    }


    override fun onItemClick(country: Country) {
        val action = AllCountriesFragmentDirections
            .actionAllCountriesFragmentToCountryFragment(country, country.name ?: "???")
        findNavController().navigate(action)
    }


    private fun initializeViews(view: View) {
        _binding = FragmentAllCountriesBinding.bind(view)
        binding.apply {
            tvBtnRetry.setOnClickListener { observeCountries() }
            rvAll.apply {
                adapter = countriesAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }


    private fun observeCountries() = lifecycleScope.launch {
        viewModel.allCountries.collect { resource ->
            when (resource) {
                is Resource.Loading -> { viewsVisibilityOnLoading() }
                is Resource.Success -> {
                    viewsVisibilityOnSuccess()
                    countriesAdapter.differ.submitList(resource.data)
                }
                is Resource.Error -> { viewsVisibilityOnError() }
            }
        }
    }


    private fun viewsVisibilityOnLoading() {
        binding.apply {
            pbLoadingAll.visibility = View.VISIBLE
        }
    }


    private fun viewsVisibilityOnSuccess() {
        binding.apply {
            pbLoadingAll.visibility = View.GONE
            llNoInternet.visibility = View.GONE
            rvAll.visibility = View.VISIBLE
        }
    }


    private fun viewsVisibilityOnError() {
        binding.apply {
            pbLoadingAll.visibility = View.GONE
            rvAll.visibility = View.GONE
            llNoInternet.visibility = View.VISIBLE
        }
    }


    private fun setPreferencesFlow() {
        themeFlow = requireContext().dataStore.data.map { preferences ->
            preferences[themeKey] ?: THEME_LIGHT
        }
    }


    private fun getPreferencesData() = lifecycleScope.launch {
        themeFlow.collect {
            viewModel.theme = it
            if (viewModel.theme == THEME_LIGHT) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }


    private fun setPreferencesData() = lifecycleScope.launch {
        requireContext().dataStore.edit { preferences ->
            val theme = preferences[themeKey] ?: THEME_LIGHT
            if (theme == THEME_LIGHT) { preferences[themeKey] = THEME_DARK }
            else { preferences[themeKey] = THEME_LIGHT }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}