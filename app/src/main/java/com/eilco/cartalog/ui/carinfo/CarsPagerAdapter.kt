package com.vitor238.cartalog.ui.carinfo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vitor238.cartalog.data.model.movie.Movie

class CarsPagerAdapter(fragmentActivity: FragmentActivity, val movie: Movie) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            CarDetailsFragment.newInstance(movie)
        } else {
            CarRecommendationsFragment.newInstance(movie.id)
        }
    }
}
