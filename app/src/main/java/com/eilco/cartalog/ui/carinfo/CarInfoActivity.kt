package com.vitor238.cartalog.ui.carinfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.vitor238.cartalog.R
import com.vitor238.cartalog.data.model.Favorite
import com.vitor238.cartalog.data.model.movie.Movie
import com.vitor238.cartalog.databinding.ActivityMovieInfoBinding
import com.vitor238.cartalog.ui.base.BaseActivity
import com.vitor238.cartalog.ui.viewmodel.FavoritesViewModel
import com.vitor238.cartalog.ui.viewmodel.FavoritesViewModelFactory
import com.vitor238.cartalog.ui.viewmodel.LoggedInViewModel
import com.vitor238.cartalog.ui.viewmodel.LoggedInViewModelFactory
import com.vitor238.cartalog.utils.ApiStatus
import com.vitor238.cartalog.utils.BaseUrls
import com.vitor238.cartalog.utils.MediaTypes
import jp.wasabeef.glide.transformations.BlurTransformation

class CarInfoActivity : BaseActivity() {

    private lateinit var binding: ActivityMovieInfoBinding
    private var movieId: Int? = null
    private lateinit var newFavorite: Favorite
    private lateinit var favoritesViewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieId = intent.extras?.getInt(MOVIE_ID)

        getMovieInfo()

        binding.content.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.content.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_white)
    }

    private fun getMovieInfo() {
        val carViewModel = ViewModelProvider(this).get(CarViewModel::class.java)

        carViewModel.getMovieInfo(movieId!!)

        carViewModel.status.observe(this) { status ->
            status?.let {
                when (it) {
                    ApiStatus.LOADING -> binding.content.viewFlipper.displayedChild = 0
                    ApiStatus.DONE -> binding.content.viewFlipper.displayedChild = 1
                    ApiStatus.ERROR -> binding.content.viewFlipper.displayedChild = 2
                }
            }
        }

        carViewModel.movieInfo.observe(this) { movie ->

            binding.content.toolbar.title = movie.title

            setupTabLayout(movie)

            Glide.with(this).load(
                BaseUrls.BASE_TMDB_IMG_URL_200 + movie.posterPath
            ).placeholder(R.drawable.ic_movie_placeholder)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(8)))
                .into(binding.content.imageCover)

            Glide.with(this).load(BaseUrls.BASE_TMDB_IMG_URL_200 + movie.posterPath)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(20, 3)))
                .placeholder(R.color.placeholder_bg_color)
                .into(binding.content.appBarImage)

            newFavorite = Favorite(
                mediaType = MediaTypes.MOVIE,
                mediaId = movie.id,
                title = movie.title ?: movie.originalTitle,
                posterPath = movie.posterPath
            )

            verifyLogin()
        }

    }

    private fun setupTabLayout(movie: Movie) {

        binding.content.viewPager.adapter = CarsPagerAdapter(this, movie)

        TabLayoutMediator(
            binding.content.tabLayout,
            binding.content.viewPager
        ) { tab, position ->
            if (position == 0) {
                tab.setText(R.string.details)
            } else {
                tab.setText(R.string.more_like_this)
            }
        }.attach()
    }

    private fun verifyLogin() {
        val loggedInViewModelFactory = LoggedInViewModelFactory(application)
        val loggedInViewModel = ViewModelProvider(this, loggedInViewModelFactory)
            .get(LoggedInViewModel::class.java)

        loggedInViewModel.firebaseUserMutableLiveData.observe(this) { firebaseUser ->
            firebaseUser?.let {
                getFavoriteState(it.uid)
            }
        }
    }


    private fun getFavoriteState(userId: String) {

        binding.content.toolbar.menu.clear()
        binding.content.toolbar.inflateMenu(R.menu.menu_favorite)

        val favoriteViewModelFactory = FavoritesViewModelFactory(userId)
        favoritesViewModel = ViewModelProvider(this, favoriteViewModelFactory)
            .get(FavoritesViewModel::class.java)

        favoritesViewModel.checkFavorite(newFavorite)

        favoritesViewModel.favorite.observe(this) { favoriteSaved ->

            val item = binding.content.toolbar.menu.findItem(R.id.action_save_to_favorites)

            if (favoriteSaved == null) {
                item.setIcon(R.drawable.ic_baseline_star_white_outline_24)
            } else {
                item.setIcon(R.drawable.ic_baseline_star_white_24)
            }

            setupFavoriteButtonClick(favoriteSaved)

        }
    }

    private fun setupFavoriteButtonClick(favoriteSaved: Favorite?) {
        binding.content.toolbar.setOnMenuItemClickListener {
            if (favoriteSaved != null) {
                favoritesViewModel.removeFavorite(favoriteSaved)
            } else {
                favoritesViewModel.saveFavorite(newFavorite)
            }
            true
        }
    }

    companion object {
        private const val MOVIE_ID = "movieId"
        fun getStartIntent(context: Context, movieId: Int): Intent {
            return Intent(context, CarInfoActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            }
        }
    }
}