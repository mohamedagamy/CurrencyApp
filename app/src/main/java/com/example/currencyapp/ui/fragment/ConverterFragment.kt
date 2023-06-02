package com.example.currencyapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.currencyapp.*
import com.example.currencyapp.databinding.FragmentConverterBinding
import com.example.currencyapp.ui.activity.MainActivity
import com.example.data.model.CurrencyResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConverterFragment : Fragment() {
    val currencyViewModel: CurrencyViewModel by activityViewModels()

    private lateinit var binding: FragmentConverterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currencyViewModel.getCurrencyData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTrendDetails.setOnClickListener {
            (activity as MainActivity).navigateToTrends()
        }

        val targetArrayAdapter: ArrayAdapter<*> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.currency_array))

        binding.targetSpinner.adapter = targetArrayAdapter

        binding.targetSpinner.onItemSelectedListener = object :OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long) {
                //Here we reload inputs
                populateInputs()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        initializeObservers()
    }

    private fun populateInputs() {
        val result = (currencyViewModel.state.value)
        if (result is CurrencyUiStateReady)
            result.apiResult?.let { showData(it) }
    }

    private fun initializeObservers() {
        lifecycleScope.launch {
            currencyViewModel.state.collect { state ->
                when (state) {
                    is Loading -> context?.showToast("Loading data...")
                    is CurrencyUiStateReady -> state.apiResult?.let { showData(it) }
                    is CurrencyUiStateError -> state.error?.let { context?.showToast(it) }
                    else -> {

                    }
                }
            }
        }
    }

    private fun showData(apiResult: CurrencyResponse) {
        val baseValue = apiResult.rates.get(binding.tvBaseCurrency.text.toString())
        val targetValue = apiResult.rates.get(binding.targetSpinner.selectedItem.toString()) ?: 0.0
        binding.etBaseCurrency.setText(baseValue.toString())
        binding.etTargetCurrency.setText(targetValue.toString())

        binding.etBaseCurrency.doAfterTextChanged {
            try {
                val currentValue = it.toString().toDouble()
                val calculatedValue = if(currentValue > 1.0) String.format("%.2f",currentValue.times(targetValue)) else currentValue.times(targetValue)
                binding.etTargetCurrency.setText(calculatedValue.toString())
            }catch (ex:NumberFormatException){
                Log.e("converter",ex.toString())
                return@doAfterTextChanged
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ConverterFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}