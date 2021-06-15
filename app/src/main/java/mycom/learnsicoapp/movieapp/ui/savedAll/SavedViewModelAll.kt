package mycom.learnsicoapp.movieapp.ui.savedAll

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mycom.learnsicoapp.movieapp.data.database.relations.MovieWithRatings
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
    var allElementMovieAPiResponse = mutableListOf<MovieResponse>()
    var allElementMovieIdAndRatingDB = mutableListOf<MovieWithRatings>()
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
                getMovieById(list)
            }
    }

    @SuppressLint("CheckResult")
    private fun getMovieById(movieWithRating: MovieWithRatings) {
        repository.getMovieByIDFromNetwork(movieWithRating.movie.movieID.toLong())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { movie ->
                allElementMovieAPiResponse.add(movie)
                allElementMovieIdAndRatingDB.add(movieWithRating)
                adapter.addMovieWithRating(
                    allElementMovieAPiResponse,
                    allElementMovieIdAndRatingDB
                )
            }
    }
}



