package com.example.data.repository

import com.example.data.api.ApiService
import com.example.data.model.CurrencyResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped

import javax.inject.Inject

@ActivityRetainedScoped
class CurrencyRepository @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun getCurrency(): CurrencyResponse = apiService.getCurrency()
    suspend fun getPopularCurrency() =  apiService.getPopularCurrency()
}