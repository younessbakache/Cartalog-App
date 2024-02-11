package com.vitor238.cartalog.ui.home.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vitor238.cartalog.databinding.FragmentHomeBinding
import com.vitor238.cartalog.ui.home.home.cars.PopularCarsAdapter
import com.vitor238.cartalog.ui.home.home.cars.PopularCarsViewModel
import com.vitor238.cartalog.ui.home.home.brands.PopularSeriesAdapter
import com.vitor238.cartalog.ui.home.home.brands.PopularSeriesViewModel
import com.vitor238.cartalog.ui.home.home.trends.TrendsAdapter
import com.vitor238.cartalog.ui.home.home.trends.TrendsViewModel
import com.vitor238.cartalog.ui.carinfo.CarInfoActivity
import com.vitor238.cartalog.ui.serieinfo.SerieInfoActivity
import com.vitor238.cartalog.utils.ApiStatus
import com.vitor238.cartalog.utils.MediaTypes

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var trendsAdapter: TrendsAdapter
    private lateinit var trendsViewModel: TrendsViewModel
    private lateinit var popularSeriesViewModel: PopularSeriesViewModel
    private lateinit var popularSeriesAdapter: PopularSeriesAdapter
    private lateinit var popularMovieViewModel: PopularCarsViewModel
    private lateinit var popularCarsAdapter: PopularCarsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        trendsAdapter = TrendsAdapter { trend ->
            if (trend.mediaType == MediaTypes.TV) {
                openSeriesInfo(trend.id)
            } else if (trend.mediaType == MediaTypes.MOVIE) {
                openMovieInfo(trend.id)
            }
        }
        binding.recyclerTrends.setHasFixedSize(true)
        binding.recyclerTrends.adapter = trendsAdapter

        popularSeriesAdapter = PopularSeriesAdapter { serie ->
            serie.id?.let {
                openSeriesInfo(it)
            }
        }
        binding.recyclerTvSeries.setHasFixedSize(true)
        binding.recyclerTvSeries.adapter = popularSeriesAdapter

        popularCarsAdapter = PopularCarsAdapter { movie ->
            movie.id?.let {
                openMovieInfo(it)
            }

        }
        binding.recyclerMovies.setHasFixedSize(true)
        binding.recyclerMovies.adapter = popularCarsAdapter
        setupViewModels()
        return binding.root
    }

    private fun setupViewModels() {
        trendsViewModel = ViewModelProvider(this).get(TrendsViewModel::class.java)
        trendsViewModel.trends.observe(viewLifecycleOwner) { trends ->
            trendsAdapter.submitList(trends)
        }
        trendsViewModel.status.observe(viewLifecycleOwner) { status ->
            status?.let {
                when (it) {
                    ApiStatus.LOADING -> binding.viewFlipperTrends.displayedChild = 0
                    ApiStatus.DONE -> binding.viewFlipperTrends.displayedChild = 1
                    ApiStatus.ERROR -> binding.viewFlipperTrends.displayedChild = 2
                }
            }
        }

        popularSeriesViewModel = ViewModelProvider(this).get(PopularSeriesViewModel::class.java)
        popularSeriesViewModel.popularTVSeries.observe(viewLifecycleOwner) { popularSeries ->
            popularSeriesAdapter.submitList(popularSeries)
        }
        popularSeriesViewModel.status.observe(viewLifecycleOwner) { status ->
            status?.let {
                when (it) {
                    ApiStatus.LOADING -> binding.viewFlipperSeries.displayedChild = 0
                    ApiStatus.DONE -> binding.viewFlipperSeries.displayedChild = 1
                    ApiStatus.ERROR -> binding.viewFlipperSeries.displayedChild = 2
                }
            }
        }

        popularMovieViewModel = ViewModelProvider(this).get(PopularCarsViewModel::class.java)
        popularMovieViewModel.popularMovies.observe(viewLifecycleOwner) { popularMovies ->
            popularCarsAdapter.submitList(popularMovies)
            Log.i(TAG, "onActivityCreated: $popularMovies")
        }

        popularMovieViewModel.status.observe(viewLifecycleOwner) { status ->
            status?.let {
                when (it) {
                    ApiStatus.LOADING -> binding.viewFlipperMovies.displayedChild = 0
                    ApiStatus.DONE -> binding.viewFlipperMovies.displayedChild = 1
                    ApiStatus.ERROR -> binding.viewFlipperMovies.displayedChild = 2
                }
            }
        }
    }

    private fun openSeriesInfo(id: Int) {
        val intent = SerieInfoActivity.getStartIntent(requireActivity(), id)
        startActivity(intent)
    }

    private fun openMovieInfo(id: Int) {
        val intent = CarInfoActivity.getStartIntent(requireActivity(), id)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = HomeFragment()
        private val TAG = HomeFragment::class.simpleName
    }
}