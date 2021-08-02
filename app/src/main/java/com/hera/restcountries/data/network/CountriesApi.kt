package com.hera.restcountries.data.network

import com.hera.restcountries.data.model.Country
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesApi {

    @GET("rest/v2/all")
    suspend fun getAllCountries() : Response<List<Country>>


    @GET("rest/v2/name/{name}")
    suspend fun getCountriesByName(
        @Path("name") query: String
    ) : Response<List<Country>>
}