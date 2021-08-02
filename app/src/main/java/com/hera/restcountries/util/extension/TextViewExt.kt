package com.hera.restcountries.util.extension

import android.widget.TextView
import com.hera.restcountries.data.model.Currency
import com.hera.restcountries.data.model.Language
import com.hera.restcountries.data.model.RegionalBlock

fun TextView.setListOfItems(strings: List<String>?) {
    strings?.let {
        var result = ""
        for (i in it.indices) {
            val item = it[i]
            if (i != it.size-1) { result += "$item\n" }
            else { result += item }
        }
        this.text = result
    }
}

fun TextView.setListOfItems(currecies: List<Currency>?): Int {
    currecies?.let {
        var result = ""
        for (i in it.indices) {
            val item = it[i]
            val str = "${item.code}, ${item.name}, ${item.symbol}"
            if (i != it.size-1) { result += "$str\n" }
            else { result += str }
        }
        this.text = result
    }
    return 0
}


fun TextView.setListOfItems(languages: List<Language>?): String {
    languages?.let {
        var result = ""
        for (i in it.indices) {
            val item = it[i]
            val str = "${item.name}, ${item.nativeName}"
            if (i != it.size-1) { result += "$str\n" }
            else { result += str }
        }
        this.text = result
    }
    return ""
}


fun TextView.setListOfItems(regionalBlocks: List<RegionalBlock>?): Long {
    regionalBlocks?.let {
        var result = ""
        for (i in it.indices) {
            val item = it[i]
            val str = "${item.acronym}, ${item.name}"
            if (i != it.size-1) { result += "$str\n" }
            else { result += str }
        }
        this.text = result
    }
    return 0L
}


fun TextView.setCountryCode(alpha2Code: String?, alpha3Code: String?) {
    var countryCode = ""
    alpha2Code?.let { "$countryCode$it, " }
    alpha3Code?.let { countryCode += it }
    this.text = countryCode
}