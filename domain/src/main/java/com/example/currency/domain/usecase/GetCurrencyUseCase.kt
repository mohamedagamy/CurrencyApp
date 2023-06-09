package com.example.currency.domain.usecase

import android.util.Log
import com.example.data.common.Resource
import com.example.data.model.CurrencyResponse
import com.example.data.repository.CurrencyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepository,
    private val defaultDispatcher: CoroutineDispatcher
) {

    private var currencyResponse = CurrencyResponse()

    suspend operator fun invoke(): Flow<Resource<CurrencyResponse>> =
        flow<Resource<CurrencyResponse>> {
            try {
                emit(Resource.loading())
                currencyResponse = repository.getCurrency()
                emit(Resource.success(currencyResponse))
            } catch (e: Throwable) {
                emit(Resource.error(e))
            }
        }.flowOn(defaultDispatcher)

}

