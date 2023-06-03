package com.example.currencyapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.currencyapp.*
import com.example.currencyapp.databinding.FragmentPopularCurrencyBinding
import com.example.currencyapp.ui.activity.MainActivity
import com.example.currencyapp.ui.adapter.CurrencyListAdapter
import com.example.data.model.Currency
import com.example.data.model.CurrencyResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularCurrencyFragment : Fragment() {

    lateinit var binding: FragmentPopularCurrencyBinding
    val currencyViewModel:CurrencyViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPopularCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeObservers()

    }

    private fun initializeObservers() {
        lifecycleScope.launch {
            currencyViewModel.state.collect { state ->
                when (state) {
                    is Loading -> context?.showToast("Loading popular data...")
                    is CurrencyUiStateReady -> state.apiResult?.let { showData(it) }
                    is CurrencyUiStateError -> state.error?.let { context?.showToast(it) }
                    else -> {

                    }
                }
            }
        }
    }

    private fun showData(response: CurrencyResponse) {
        val currencyList = resources.getStringArray(R.array.currency_array_popular).asList()
        val rates =  response.rates.filter { currencyList.contains(it.key) }.map {
            Currency(it.key,it.value.toString()) }.filterNot { it.currencyKey.equals(response.base) }
        Log.e("rates",rates.toString())
        val adapter = CurrencyListAdapter()
        binding.rvPopularCurrency.adapter = adapter
        adapter.submitList(rates)
        binding.tvBaseCurrency.setText(response.base.toString())
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PopularCurrencyFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}