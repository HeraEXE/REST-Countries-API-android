package com.hera.restcountries.data.repository

import com.hera.restcountries.data.network.CountriesApi
import com.hera.restcountries.util.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class Repository @Inject constructor(
    private val countriesApi: CountriesApi
) {

    fun getAllCountries() = flow {
        emit(Resource.Loading())
        val response = try {
            countriesApi.getAllCountries()
        } catch (e: IOException) {
            emit(Resource.Error(e.message))
            return@flow
        } catch (e: HttpException) {
            emit(Resource.Error(e.message))
            return@flow
        }
        if (response.isSuccessful && response.body() != null) {
            emit(Resource.Success(response.body()!!))
        }
    }


    fun getCountriesByName(query: String) = flow {
        emit(Resource.Loading())
        val response = try {
            countriesApi.getCountriesByName(query)
        } catch (e: IOException) {
            emit(Resource.Error(e.message))
            return@flow
        } catch (e: HttpException) {
            emit(Resource.Error(e.message))
            return@flow
        }
        if (response.isSuccessful && response.body() != null) {
            emit(Resource.Success(response.body()!!))
        } else {
            emit(Resource.Error("No results"))
            return@flow
        }
    }
}