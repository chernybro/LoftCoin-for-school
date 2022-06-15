package com.chernybro.loftcoin.ui.activity.welcome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chernybro.loftcoin.R
import com.chernybro.loftcoin.databinding.WelcomePageBinding

class WelcomeAdapter : RecyclerView.Adapter<WelcomeAdapter.ViewHolder>() {
    companion object {
        private val IMAGES = intArrayOf(
            R.drawable.welcome_page_1,
            R.drawable.welcome_page_2,
            R.drawable.welcome_page_3
        )
        private val TITLES = intArrayOf(
            R.string.welcome_page_1_title,
            R.string.welcome_page_2_title,
            R.string.welcome_page_3_title
        )
        private val SUBTITLES = intArrayOf(
            R.string.welcome_page_1_subtitle,
            R.string.welcome_page_2_subtitle,
            R.string.welcome_page_3_subtitle
        )
    }

    private var inflater: LayoutInflater? = null
    override fun getItemCount(): Int {
        return IMAGES.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            WelcomePageBinding.inflate(
                inflater!!, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.image.setImageResource(IMAGES[position])
        holder.binding.title.setText(TITLES[position])
        holder.binding.subtitle.setText(SUBTITLES[position])
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        inflater = LayoutInflater.from(recyclerView.context)
    }

    class ViewHolder(val binding: WelcomePageBinding) : RecyclerView.ViewHolder(
        binding.root
    )
}