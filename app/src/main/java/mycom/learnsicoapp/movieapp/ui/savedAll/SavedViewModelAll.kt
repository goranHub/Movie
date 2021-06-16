package mycom.learnsicoapp.movieapp.ui.savedAll

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mycom.learnsicoapp.movieapp.data.database.relations.MovieAndRatings
import mycom.learnsicoapp.movieapp.data.remote.firebase.FireStoreClass
import mycom.learnsicoapp.movieapp.data.remote.response.movie.MovieResponse
import mycom.learnsicoapp.movieapp.domain.Repository

/**
 * @author ll4
 * @date 12/6/2020
 */
class SavedViewModelAll @ViewModelInject constructor(
    private val repository: Repository,
    fireStoreClass: FireStoreClass
) : ViewModel() {

    val adapter = SavedAdapterAll()
    var movieResponse = mutableListOf<MovieResponse>()
    var movieRatings = mutableListOf<MovieAndRatings>()
    val currentUser = fireStoreClass.currentUserID()

    init {
        getMovieAndRatingForCurrentUserFromDb()
    }

    @SuppressLint("CheckResult")
    private fun getMovieAndRatingForCurrentUserFromDb() {
        repository.getRatingsOfMovie()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                getMovieByIdFromNetwork(list)
            }
    }

    @SuppressLint("CheckResult")
    private fun getMovieByIdFromNetwork(movieWithRating: List<MovieAndRatings>) {
        movieWithRating.map {
            repository.getMovieByIDFromNetwork(it.movie.movieID.toLong())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { movie ->
                    movieResponse.add(movie)
                    movieRatings.add(it)
                    adapter.addMovieWithRating(movieResponse, movieRatings)
                }
        }
    }
}



