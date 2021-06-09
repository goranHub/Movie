package mycom.learnsicoapp.movieapp.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import mycom.learnsicoapp.movieapp.ui.popular.PopularMovieAdapter
import mycom.learnsicoapp.movieapp.ui.popular.PopularMovieFragment
import mycom.learnsicoapp.movieapp.ui.search.SearchAdapter
import mycom.learnsicoapp.movieapp.ui.search.SearchFragment
import mycom.learnsicoapp.movieapp.ui.topmovie.TopMovieAdapter
import mycom.learnsicoapp.movieapp.ui.topmovie.TopMovieFragment
import javax.inject.Inject

class MovieFragmentFactory @Inject constructor(
    private val popularAdapter : PopularMovieAdapter,
    private val topMovieAdapter : TopMovieAdapter,
    private val searchAdapter : SearchAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            PopularMovieFragment::class.java.name -> PopularMovieFragment(popularAdapter)
            TopMovieFragment::class.java.name -> TopMovieFragment(topMovieAdapter)
            SearchFragment::class.java.name -> SearchFragment(searchAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}