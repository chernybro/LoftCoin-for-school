package com.chernybro.loftcoin.ui.rates

import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chernybro.loftcoin.BuildConfig
import com.chernybro.loftcoin.R
import com.chernybro.loftcoin.data.models.Coin
import com.chernybro.loftcoin.databinding.IRateBinding
import com.chernybro.loftcoin.utils.OutlineCircle
import com.chernybro.loftcoin.utils.formatters.Formatter
import com.squareup.picasso.Picasso
import java.util.*

class RatesAdapter(private val priceFormatter: Formatter<Double>) : ListAdapter<Coin, RatesAdapter.ViewHolder>(DIFF_UTIL) {
    private lateinit var inflater: LayoutInflater
    private var colorNegative = Color.RED
    private var colorPositive = Color.GREEN

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Coin>() {
            override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(IRateBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coin = getItem(position)
        holder.binding.symbol.text = coin.symbol
        holder.binding.price.text = priceFormatter.format(coin.quote.values.iterator().next().price)
        holder.binding.change.text = String.format(Locale.US, "%.3f%%", coin.quote.values.iterator().next().change24h)
        if ((coin.quote.values.iterator().next().change24h) > 0) {
            holder.binding.change.setTextColor(colorPositive)
        } else {
            holder.binding.change.setTextColor(colorNegative)
        }
        Picasso.get()
            .load(BuildConfig.IMG_ENDPOINT + coin.id + ".png")
            .into(holder.binding.logo)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val context = recyclerView.context
        inflater = LayoutInflater.from(context)
        val v = TypedValue()
        context.theme.resolveAttribute(R.attr.textNegative, v, true)
        colorNegative = v.data
        context.theme.resolveAttribute(R.attr.textPositive, v, true)
        colorPositive = v.data
    }

    class ViewHolder(val binding: IRateBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        init {
            OutlineCircle.apply(binding.logo)
        }
    }

    init {
        setHasStableIds(true)
    }
}