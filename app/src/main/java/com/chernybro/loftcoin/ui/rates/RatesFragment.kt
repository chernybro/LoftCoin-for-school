package com.chernybro.loftcoin.ui.rates

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chernybro.loftcoin.BaseComponent
import com.chernybro.loftcoin.LoftApp
import com.chernybro.loftcoin.R
import com.chernybro.loftcoin.data.remote.models.Coin
import com.chernybro.loftcoin.databinding.FragmentRatesBinding
import javax.inject.Inject

class RatesFragment @Inject constructor(

): Fragment() {
    private var _binding: FragmentRatesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RatesAdapter
    private lateinit var viewModel: RatesViewModel

    private lateinit var component: RatesComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = DaggerRatesComponent.builder()
            .baseComponent((activity?.application as LoftApp).getComponent())
            .build()
        viewModel = ViewModelProvider(this, component.viewModelFactory())[RatesViewModel::class.java]
        adapter = component.ratesAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.recycler.layoutManager = LinearLayoutManager(view.context)
        binding.recycler.swapAdapter(adapter, false)
        binding.recycler.setHasFixedSize(true)
        binding.refresher.setOnRefreshListener { viewModel.refresh() }
        viewModel.coins()
            .observe(viewLifecycleOwner) { list: List<Coin?> -> adapter.submitList(list) }
        viewModel.isRefreshing().observe(viewLifecycleOwner) { isRefreshing ->
            binding.refresher.isRefreshing = isRefreshing!!
        }

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.rates, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (R.id.currency_dialog == item.itemId) {
            NavHostFragment.findNavController(this)
                .navigate(R.id.currency_dialog)
            return true
        } else if (R.id.sort_dialog == item.itemId) {
            viewModel.switchSortingOrder()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        binding.recycler.swapAdapter(null, false)
        super.onDestroyView()
    }
}