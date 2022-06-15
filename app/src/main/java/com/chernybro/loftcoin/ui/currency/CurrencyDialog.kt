package com.chernybro.loftcoin.ui.currency

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chernybro.loftcoin.R
import com.chernybro.loftcoin.data.models.Currency
import com.chernybro.loftcoin.data.service.currency.CurrencyRepo
import com.chernybro.loftcoin.data.service.currency.CurrencyRepoImpl
import com.chernybro.loftcoin.databinding.DialogCurrencyBinding
import com.chernybro.loftcoin.utils.OnItemClick
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CurrencyDialog : AppCompatDialogFragment() {
    private var _binding: DialogCurrencyBinding? = null
    private val binding get() = _binding!!

    private var currencyRepo: CurrencyRepo? = null
    private lateinit var adapter: CurrencyAdapter
    private lateinit var onItemClick: OnItemClick

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currencyRepo = CurrencyRepoImpl(requireContext())
        adapter = CurrencyAdapter()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogCurrencyBinding.inflate(layoutInflater)
        return MaterialAlertDialogBuilder(requireActivity())
            .setTitle(R.string.choose_currency)
            .setView(binding.root)
            .create()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.recycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.recycler.adapter = adapter
        currencyRepo!!.availableCurrencies().observe(this
        ) { list: List<Currency> ->
            adapter.submitList(list)
        }
        onItemClick = OnItemClick(binding.recycler.context) { v: View ->
            val viewHolder = binding.recycler.findContainingViewHolder(v)
            if (viewHolder != null) {
                val item = adapter.getItem(viewHolder.adapterPosition)
                currencyRepo!!.updateCurrency(item)
            }
            dismissAllowingStateLoss()
        }
        binding.recycler.addOnItemTouchListener(onItemClick)
    }

    override fun onDestroyView() {
        binding.recycler.removeOnItemTouchListener(onItemClick)
        binding.recycler.adapter = null
        super.onDestroyView()
    }

}