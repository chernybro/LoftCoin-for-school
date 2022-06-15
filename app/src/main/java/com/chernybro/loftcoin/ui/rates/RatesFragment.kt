package com.chernybro.loftcoin.ui.rates

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chernybro.loftcoin.R
import com.chernybro.loftcoin.data.models.Coin
import com.chernybro.loftcoin.data.models.Currency
import com.chernybro.loftcoin.data.service.currency.CurrencyRepo
import com.chernybro.loftcoin.data.service.currency.CurrencyRepoImpl
import com.chernybro.loftcoin.databinding.FragmentRatesBinding
import com.chernybro.loftcoin.utils.formatters.PriceFormatter
import timber.log.Timber

class RatesFragment : Fragment() {
    private var _binding: FragmentRatesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RatesAdapter
    private lateinit var viewModel: RatesViewModel
    private var currencyRepo: CurrencyRepo? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RatesViewModel::class.java)
        adapter = RatesAdapter(PriceFormatter())
        currencyRepo = CurrencyRepoImpl(requireContext())
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
        currencyRepo!!.currency()
            .observe(viewLifecycleOwner) { currency: Currency? -> Timber.d("%s", currency) }
    }

    override fun onStart() {
        super.onStart()
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
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        binding.recycler.swapAdapter(null, false)
        super.onDestroyView()
    }
}