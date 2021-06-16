package mycom.learnsicoapp.movieapp.ui.saved

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mycom.learnsicoapp.movieapp.data.database.entities.MovieEntity
import mycom.learnsicoapp.movieapp.data.database.entities.Rating
import mycom.learnsicoapp.movieapp.data.database.relations.MovieAndRating
import mycom.learnsicoapp.movieapp.data.remote.firebase.FireStoreClass
import mycom.learnsicoapp.movieapp.domain.Repository

/**
 * @author ll4
 * @date 12/6/2020
 */
class SavedViewModel @ViewModelInject constructor(
    private val repository: Repository,
    fireStoreClass: FireStoreClass
) : ViewModel() {

    val adapter = SavedAdapter()
    var allMovieAPiResponse =
        mutableListOf<mycom.learnsicoapp.movieapp.data.remote.response.movie.MovieResponse>()
    var allRatingsDB = mutableListOf<Rating?>()
    val currentUser = fireStoreClass.currentUserID()

    init {
        getMovieAndRatingForCurrentUserFromDb()
    }

    @SuppressLint("CheckResult")
    private fun getMovieAndRatingForCurrentUserFromDb() {
        repository.getRatingsOfUser(currentUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                list.map {
                    it.movieList.forEach { getRatingByMovieD(it) }
                }
            }
    }


    @SuppressLint("CheckResult")
    private fun getRatingByMovieD(movie: MovieEntity) {
        repository
            .getRating(movie.movieID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val it = it
                    getMovieById(movie, it.rating)
            }
    }

    @SuppressLint("CheckResult")
    private fun getMovieById(movie: MovieEntity, rating: Rating?) {
        repository.getMovieByIDFromNetwork(movie.movieID.toLong())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { movie ->
                allMovieAPiResponse.add(movie)
                allRatingsDB.add(rating)
                adapter.addMovieWithRating(allMovieAPiResponse, allRatingsDB)
            }
    }
}



