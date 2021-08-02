package com.hera.restcountries.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hera.restcountries.data.model.Country
import com.hera.restcountries.databinding.ItemCountryBinding
import com.hera.restcountries.util.extension.loadSvg

class CountriesAdapter(
    private val listener: Listener
    ) : RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    interface Listener{ fun onItemClick(country: Country) }


    inner class ViewHolder(
        private val binding: ItemCountryBinding
        ) : RecyclerView.ViewHolder(binding.root) {


        fun onBind(position: Int) {
            val country = differ.currentList[position]
            country.apply {
                binding.apply {
                    flag?.let { ivCountryFlag.loadSvg(it) }
                    name?.let { tvCountryName.text = it }
                    nativeName?.let { tvCountryNativeName.text = it }
                    llItemCountry.setOnClickListener { listener.onItemClick(country) }
                }
            }
        }
    }


    private val diffCallback = object : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country) =
            oldItem.flag == newItem.flag
        override fun areContentsTheSame(oldItem: Country, newItem: Country) =
            oldItem == newItem
    }
    val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind(position)


    override fun getItemCount() = differ.currentList.size
}