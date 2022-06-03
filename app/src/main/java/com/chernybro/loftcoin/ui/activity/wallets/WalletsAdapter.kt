package com.chernybro.loftcoin.ui.activity.wallets

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chernybro.loftcoin.databinding.IWalletBinding

internal class WalletsAdapter : RecyclerView.Adapter<WalletsAdapter.ViewHolder>() {
    private var inflater: LayoutInflater? = null
    override fun getItemCount(): Int {
        return 10
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            IWalletBinding.inflate(
                inflater!!, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        inflater = LayoutInflater.from(recyclerView.context)
    }

    internal class ViewHolder(binding: IWalletBinding) : RecyclerView.ViewHolder(binding.root)
}