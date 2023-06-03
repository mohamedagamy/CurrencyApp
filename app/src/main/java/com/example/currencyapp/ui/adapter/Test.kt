package com.example.currencyapp.ui.adapter
import android.view.View
import com.example.currencyapp.R
import com.example.currencyapp.databinding.ItemCurrencyBinding
import com.example.currencyapp.databinding.ItemPopularCurrencyBinding
import com.example.data.model.Currency
import com.xwray.groupie.viewbinding.BindableItem


class CurrencyItem(val currency: Currency) : BindableItem<ItemCurrencyBinding>() {

    override fun bind(binding: ItemCurrencyBinding, position: Int) {
        binding.setCurrency(currency)
    }

    override fun getLayout(): Int {
        return R.layout.item_currency
    }

    override fun initializeViewBinding(view: View): ItemCurrencyBinding {
        return ItemCurrencyBinding.bind(view)
    }
}