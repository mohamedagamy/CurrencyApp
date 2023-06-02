package com.example.currencyapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.R
import com.example.currencyapp.databinding.ItemPopularCurrencyBinding
import com.example.data.model.Currency

class CurrencyListAdapter : ListAdapter<Currency, CurrencyListAdapter.MyViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemPopularCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MyViewHolder(val binding: ItemPopularCurrencyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Currency) = with(itemView) {
            binding.tvTargetCurrencyName.setText(item.currencyKey)
            binding.tvTargetCurrencyValue.setText(item.currencyValue)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.currencyKey == newItem.currencyKey
    }

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.currencyValue == newItem.currencyValue
    }
}