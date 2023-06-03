package com.example.currencyapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.currencyapp.*
import com.example.currencyapp.databinding.FragmentHistoryBinding
import com.example.currencyapp.ui.adapter.CurrencyItem
import com.example.data.model.Currency
import com.example.data.model.CurrencyResponse
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.Group
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.example.item.HeaderItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    var groupieAdapter: GroupieAdapter = GroupieAdapter()
    lateinit var binding: FragmentHistoryBinding
    val currencyViewModel: CurrencyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvHistoricalRates.adapter = groupieAdapter
        groupieAdapter.clear()

        initializeObservers()
        val daysList = get3DaysAgo()
        lifecycleScope.launch {
            val firstDayJob = currencyViewModel.getHistoricalRate(daysList.get(0))
            val secondDayJob = currencyViewModel.getHistoricalRate(daysList.get(1))
            val thirdDayJob = currencyViewModel.getHistoricalRate(daysList.get(2))

            firstDayJob.await()
            secondDayJob.await()
            thirdDayJob.await()
        }
    }

    private fun initializeObservers() {
        lifecycleScope.launch {
            currencyViewModel.state.collect { state ->
                when (state) {
                    is Loading -> context?.showToast("Loading history data...")
                    is CurrencyUiStateReady -> state.apiResult?.let { showData(it) }
                    is CurrencyUiStateError -> state.error?.let { context?.showToast(it) }
                    else -> {

                    }
                }
            }
        }
    }

    private fun showData(it: CurrencyResponse) {
        Log.e("day...",it.date.toString())

        val section = Section()
        val currencyItems =  it.rates.filterNot { it.key.equals("EUR") }.map { CurrencyItem(Currency(it.key,it.value.toString())) }
        section.setHeader(HeaderItem(it.date.toString()))
        section.addAll(currencyItems)
        groupieAdapter.add(section)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}