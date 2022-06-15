package com.chernybro.loftcoin.ui.wallets

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.chernybro.loftcoin.R
import com.chernybro.loftcoin.databinding.FragmentWalletsBinding
import com.chernybro.loftcoin.ui.activity.wallets.WalletsAdapter
import kotlin.math.abs
import kotlin.math.pow

class WalletsFragment : Fragment() {
    private var walletsSnapHelper: SnapHelper? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wallets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentWalletsBinding.bind(view)
        walletsSnapHelper = PagerSnapHelper()
        (walletsSnapHelper as PagerSnapHelper).attachToRecyclerView(binding.recycler)
        val value = TypedValue()
        view.context.theme.resolveAttribute(R.attr.walletCardWidth, value, true)
        val displayMetrics = view.context.resources.displayMetrics
        val padding = (displayMetrics.widthPixels - value.getDimension(displayMetrics)).toInt() / 2
        binding.recycler.setPadding(padding, 0, padding, 0)
        binding.recycler.clipToPadding = false
        binding.recycler.addOnScrollListener(CarouselScroller())
        binding.recycler.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        binding.recycler.adapter = WalletsAdapter()
        binding.recycler.visibility = View.VISIBLE
        binding.walletCard.visibility = View.GONE
    }

    override fun onDestroyView() {
        walletsSnapHelper!!.attachToRecyclerView(null)
        super.onDestroyView()
    }

    private class CarouselScroller : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val centerX = (recyclerView.left + recyclerView.right) / 2
            for (i in 0 until recyclerView.childCount) {
                val child = recyclerView.getChildAt(i)
                val childCenterX = (child.left + child.right) / 2
                val childOffset =
                    abs(centerX - childCenterX) / centerX.toFloat() // 1.2, 0, 1.2
                val factor = 0.85.pow(childOffset.toDouble())
                    .toFloat() // 2^1/1.2, 2^0, 2^// 0.4, 1, 0.4
                child.scaleX = factor
                child.scaleY = factor
            }
        }
    }
}