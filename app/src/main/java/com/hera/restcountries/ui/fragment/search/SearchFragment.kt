package com.hera.restcountries.ui.fragment.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hera.restcountries.R
import com.hera.restcountries.data.model.Country
import com.hera.restcountries.databinding.FragmentSearchBinding
import com.hera.restcountries.ui.MainActivity
import com.hera.restcountries.ui.MainViewModel
import com.hera.restcountries.ui.adapter.CountriesAdapter
import com.hera.restcountries.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), CountriesAdapter.Listener {

    private val args: SearchFragmentArgs by navArgs()
    private val countriesAdapter = CountriesAdapter(this)
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).mainViewModel
        viewModel.query.value = args.query
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(view)
        getPreservedDataOrLoadNew()

    }


    private fun initializeViews(view: View) {
        _binding = FragmentSearchBinding.bind(view)
        binding.apply {
            tvBtnRetrySearch.setOnClickListener { observeCountries() }
            rvSearch.apply {
                adapter = countriesAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }


    private fun getPreservedDataOrLoadNew() {
        if (viewModel.countriesByName != null)
            countriesAdapter.differ.submitList(viewModel.countriesByName)
        else
            observeCountries()
    }


    private fun observeCountries() = lifecycleScope.launch {
        viewModel.countriesByNameFlow.collect { resource ->
            when (resource) {
                is Resource.Loading -> { viewsVisibilityOnLoading() }
                is Resource.Success -> {
                    viewsVisibilityOnSuccess()
                    viewModel.countriesByName = resource.data
                    countriesAdapter.differ.submitList(viewModel.countriesByName)
                }
                is Resource.Error -> {
                    if (resource.message == "No results") {
                        viewsVisibilityOnNoResults()
                    } else {
                        viewsVisibilityOnError()
                    }
                }
            }
        }
    }


    private fun viewsVisibilityOnLoading() {
        binding.apply {
            pbLoadingSearch.visibility = View.VISIBLE
        }
    }


    private fun viewsVisibilityOnSuccess() {
        binding.apply {
            pbLoadingSearch.visibility = View.GONE
            llNoInternetSearch.visibility = View.GONE
            llNoResultsSearch.visibility = View.GONE
            rvSearch.visibility = View.VISIBLE
        }
    }


    private fun viewsVisibilityOnError() {
        binding.apply {
            pbLoadingSearch.visibility = View.GONE
            rvSearch.visibility = View.GONE
            llNoResultsSearch.visibility = View.GONE
            llNoInternetSearch.visibility = View.VISIBLE
        }
    }


    private fun viewsVisibilityOnNoResults() {
        binding.apply {
            pbLoadingSearch.visibility = View.GONE
            rvSearch.visibility = View.GONE
            llNoInternetSearch.visibility = View.GONE
            llNoResultsSearch.visibility = View.VISIBLE
        }
    }


    override fun onItemClick(country: Country) {
        val action = SearchFragmentDirections
            .actionSearchFragmentToCountryFragment(country, country.name ?: "???")
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}