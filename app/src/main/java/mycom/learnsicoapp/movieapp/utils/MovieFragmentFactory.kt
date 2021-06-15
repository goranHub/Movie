package mycom.learnsicoapp.movieapp.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import mycom.learnsicoapp.movieapp.data.remote.firebase.FireStoreClass
import mycom.learnsicoapp.movieapp.ui.detail.DetailsMovieFragment
import mycom.learnsicoapp.movieapp.ui.login.signIn.SignInFragment
import mycom.learnsicoapp.movieapp.ui.login.signup.SignUpFragment
import mycom.learnsicoapp.movieapp.ui.popular.PopularMovieAdapter
import mycom.learnsicoapp.movieapp.ui.popular.PopularMovieFragment
import mycom.learnsicoapp.movieapp.ui.profil.MyProfileFragment
import mycom.learnsicoapp.movieapp.ui.search.SearchAdapter
import mycom.learnsicoapp.movieapp.ui.search.SearchFragment
import mycom.learnsicoapp.movieapp.ui.topmovie.TopMovieAdapter
import mycom.learnsicoapp.movieapp.ui.topmovie.TopMovieFragment
import javax.inject.Inject

class MovieFragmentFactory @Inject constructor(
    private val popularAdapter : PopularMovieAdapter,
    private val topMovieAdapter : TopMovieAdapter,
    private val searchAdapter : SearchAdapter,
    private val fireStoreClass: FireStoreClass
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            PopularMovieFragment::class.java.name -> PopularMovieFragment(popularAdapter)
            TopMovieFragment::class.java.name -> TopMovieFragment(topMovieAdapter)
            SearchFragment::class.java.name -> SearchFragment(searchAdapter)
            DetailsMovieFragment::class.java.name -> DetailsMovieFragment(fireStoreClass)
            MyProfileFragment::class.java.name -> MyProfileFragment(fireStoreClass)
            SignUpFragment::class.java.name -> SignUpFragment(fireStoreClass)
            SignInFragment::class.java.name -> SignInFragment(fireStoreClass)
            else -> super.instantiate(classLoader, className)
        }
    }
}