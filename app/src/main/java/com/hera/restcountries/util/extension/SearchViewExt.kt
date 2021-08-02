package com.hera.restcountries.util.extension

import androidx.appcompat.widget.SearchView

fun SearchView.setOnQueryListener(listener: (String) -> Unit) {
    this.setOnQueryTextListener(
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    listener(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?) = true
        }
    )
}