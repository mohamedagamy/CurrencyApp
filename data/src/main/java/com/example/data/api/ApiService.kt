package com.example.data.api

import com.example.data.model.CurrencyResponse
import retrofit2.http.*

//https://data.fixer.io/api/latest?access_key=0cc6c9fd211ecc4787b293ff65badb08
interface ApiService {
    @GET("latest?access_key=0cc6c9fd211ecc4787b293ff65badb08")
    suspend fun getCurrency(): CurrencyResponse

    @GET("latest?access_key=0cc6c9fd211ecc4787b293ff65badb08")
    suspend fun getPopularCurrency(@Query("symbols") symbols:String = "GBP,EUR,USD,CAD,CHF,AUD,JPY,NZD,CNY,SGD"): CurrencyResponse
}
