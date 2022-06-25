package com.chernybro.loftcoin.ui.currency

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chernybro.loftcoin.BaseComponent
import com.chernybro.loftcoin.LoftApp
import com.chernybro.loftcoin.R
import com.chernybro.loftcoin.databinding.DialogCurrencyBinding
import com.chernybro.loftcoin.utils.handlers.OnItemClick
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject

class CurrencyDialog @Inject constructor(
) : AppCompatDialogFragment() {
    private var _binding: DialogCurrencyBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CurrencyAdapter
    private lateinit var onItemClick: OnItemClick

    @JvmField
    @Inject
    var baseComponent: BaseComponent? = null

    private lateinit var component: CurrencyComponent

    private lateinit var viewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = DaggerCurrencyComponent.builder()
            .baseComponent(baseComponent)
            .build()
        viewModel =
            ViewModelProvider(this, component.viewModelFactory())[CurrencyViewModel::class.java]
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
        viewModel.allCurrencies().observe(this) { currencies ->
            adapter.submitList(currencies)
        }
        onItemClick = OnItemClick(binding.recycler.context) { v: View ->
            val viewHolder = binding.recycler.findContainingViewHolder(v)
            if (viewHolder != null) {
                viewModel.updateCurrency(adapter.getItem(viewHolder.adapterPosition))
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