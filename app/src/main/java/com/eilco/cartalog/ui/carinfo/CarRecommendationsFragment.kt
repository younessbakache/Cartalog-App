package com.vitor238.cartalog.ui.carinfo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vitor238.cartalog.databinding.FragmentMovieRecommendationsBinding
import com.vitor238.cartalog.utils.ApiStatus

private const val MOVIE_ID = "movieId"

class CarRecommendationsFragment : Fragment() {
    private var movieId: Int? = null
    private lateinit var carRecommendationAdapter: CarRecommendationAdapter
    private var _binding: FragmentMovieRecommendationsBinding? = null
    private val binding: FragmentMovieRecommendationsBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(MOVIE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieRecommendationsBinding.inflate(layoutInflater, container, false)

        carRecommendationAdapter = CarRecommendationAdapter {
            val intent = Intent(requireActivity(), CarInfoActivity::class.java)
            intent.putExtra("movieId", it.id)
            startActivity(intent)
        }

        binding.content.recyclerRecommendations.setHasFixedSize(true)
        binding.content.recyclerRecommendations.adapter = carRecommendationAdapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val carRecommendationViewModel = ViewModelProvider(this)
            .get(CarRecommendationViewModel::class.java)

        movieId?.let { id ->
            carRecommendationViewModel.getRecommendations(id)

            carRecommendationViewModel.movieRecommendation.observe(viewLifecycleOwner) { recommendations ->
                carRecommendationAdapter.submitList(recommendations)
            }

            carRecommendationViewModel.status.observe(viewLifecycleOwner) { status ->
                status?.let {
                    when (it) {
                        ApiStatus.LOADING -> binding.content.viewFlipper.displayedChild = 0
                        ApiStatus.DONE -> binding.content.viewFlipper.displayedChild = 1
                        ApiStatus.ERROR -> binding.content.viewFlipper.displayedChild = 2
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(movieId: Int) =
            CarRecommendationsFragment().apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_ID, movieId)
                }
            }
    }
}