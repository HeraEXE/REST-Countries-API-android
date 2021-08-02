package com.hera.restcountries.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Country(
    val name: String?,
    val topLevelDomain: List<String>?,
    val alpha2Code: String?,
    val alpha3Code: String?,
    val callingCodes: List<String>?,
    val capital: String?,
    val altSpellings: List<String>?,
    val region: String?,
    val subregion: String?,
    val population: Long?,
    val latlng: List<Double>?,
    val demonym: String?,
    val area: Double?,
    val gini: Double?,
    val timezones: List<String>?,
    val borders: List<String>?,
    val nativeName: String?,
    val numericCode: String?,
    val currencies: List<Currency>?,
    val languages: List<Language>?,
    val translations: Translations?,
    val flag: String?,
    val regionalBlocks: List<RegionalBlock>?,
    val cioc: String?
) : Parcelable


@Parcelize
data class Currency(
    val code: String?,
    val name: String?,
    val symbol: String?
) : Parcelable


@Parcelize
data class Language(
    @SerializedName("iso639_1")
    val iso1: String?,
    @SerializedName("iso639_2")
    val iso2: String?,
    val name: String?,
    val nativeName: String?
) : Parcelable


@Parcelize
data class Translations(
    val de: String?,
    val es: String?,
    val fr: String?,
    val ja: String?,
    val it: String?,
    val br: String?,
    val pt: String?,
    val nr: String?,
    val hr: String?,
    val fa: String?
) : Parcelable


@Parcelize
data class RegionalBlock(
    val acronym: String?,
    val name: String?,
    val otherAcronyms: List<String>?,
    val otherNames: List<String>?
) : Parcelable
