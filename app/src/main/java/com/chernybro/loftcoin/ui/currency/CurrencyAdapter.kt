package com.chernybro.loftcoin.ui.currency


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chernybro.loftcoin.data.models.Currency
import com.chernybro.loftcoin.databinding.ICurrencyBinding

class CurrencyAdapter : ListAdapter<Currency, CurrencyAdapter.ViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Currency>() {
            override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var inflater: LayoutInflater? = null

    public override fun getItem(position: Int): Currency {
        return super.getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ICurrencyBinding.inflate(
                inflater!!, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (symbol, _, name) = getItem(position)
        holder.binding.name.text = name
        holder.binding.symbol.text = symbol
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        inflater = LayoutInflater.from(recyclerView.context)
    }

    class ViewHolder(val binding: ICurrencyBinding) : RecyclerView.ViewHolder(
        binding.root
    )
}