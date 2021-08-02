package com.hera.restcountries.ui.fragment.country

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.hera.restcountries.R
import com.hera.restcountries.databinding.FragmentCountryBinding
import com.hera.restcountries.ui.MainActivity
import com.hera.restcountries.ui.MainViewModel
import com.hera.restcountries.util.extension.loadSvg
import com.hera.restcountries.util.extension.setCountryCode
import com.hera.restcountries.util.extension.setListOfItems
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class CountryFragment : Fragment(R.layout.fragment_country) {

    private val args: CountryFragmentArgs by navArgs()
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).mainViewModel
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCountryBinding.bind(view)
        setDataToViews()
    }


    private fun setDataToViews() {
        args.country.apply {
            binding.apply {
                flag?.let { ivFlagCountry.loadSvg(it) }
                name?.let { tvNameCountry.text = it }
                nativeName?.let { tvNativeNameCountry.text = it }
                capital?.let { tvCapitalCountry.text = it }
                tvDomainCountry.setListOfItems(topLevelDomain)
                tvCodeCountry.setCountryCode(alpha2Code, alpha3Code)
                latlng?.let {
                    tvLatCountry.text = it[0].roundToInt().toString()
                    tvLngCountry.text = it[1].roundToInt().toString()
                }
                area?.let { tvAreaCountry.text = it.toString() }
                region?.let { tvRegionCountry.text = it }
                subregion?.let { tvSubregionCountry.text = it }
                population?.let { tvPopulationCountry.text = it.toString() }
                demonym?.let { tvDemonymCountry.text = it }
                tvBordersCountry.setListOfItems(borders)
                tvTimezonesCountry.setListOfItems(timezones)
                tvLanguagesCountry.setListOfItems(languages)
                tvCurrenciesCountry.setListOfItems(currencies)
                tvRegionalBlocksCountry.setListOfItems(regionalBlocks)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}