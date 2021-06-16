package mycom.learnsicoapp.movieapp.ui.detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mycom.learnsicoapp.movieapp.data.database.entities.MovieEntity
import mycom.learnsicoapp.movieapp.data.database.entities.Rating
import mycom.learnsicoapp.movieapp.data.database.relations.MovieAndRating
import mycom.learnsicoapp.movieapp.data.database.relations.MovieRatingCrossRef
import mycom.learnsicoapp.movieapp.data.database.relations.MovieAndRatings
import mycom.learnsicoapp.movieapp.data.database.relations.UserMovieCrossRef
import mycom.learnsicoapp.movieapp.data.remote.response.tvShow.TvResponse
import mycom.learnsicoapp.movieapp.domain.Repository
import mycom.learnsicoapp.movieapp.utils.URL_IMAGE

class DetailsViewModel @ViewModelInject constructor(
    private var repository: Repository,
) : ViewModel() {

    var bindDetails = BindDetails()


    suspend fun insertUserMovieCrossRef(crossRef: UserMovieCrossRef) {
        repository.insertUserMovieCrossRef(crossRef)
    }

    suspend fun insertMovieRatingCrossRef(crossRef: MovieRatingCrossRef) {
        repository.insertMovieRatingCrossRef(crossRef)
    }

    fun insertRating(rating: Rating) {
        repository.insertRating(rating)
    }

    fun insertMovie(movie: MovieEntity) {
        repository.insertMovie(movie)
    }

    fun getMovieAndRatingWithMovieID(movieID: String): Observable<MovieAndRatings> {
        return repository.getMovieAndRatingWithMovieID(movieID)
    }


    fun getRating(movieID: String): Observable<MovieAndRating> {
        return repository.getRating(movieID)
    }

    fun deleteSmileyByMovieId(movieID: String, userID: String) {
        repository.deleteSmileyByMovieId(movieID, userID)
    }

    @SuppressLint("CheckResult")
    fun getMovieByID(movieId: Long) {
        repository
            .getMovieByIDFromNetwork(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                bindDetails.imageUrl = URL_IMAGE + it.posterPath
                bindDetails.overview = it.overview
                bindDetails.popularity = it.popularity
                bindDetails.releaseDate = it.releaseDate
            }
    }

    fun getTvShowById(movieId: Long) {
        repository
            .getTvShowById(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<TvResponse> {
                    override fun onSubscribe(d: Disposable) {
                    }
                    override fun onNext(response: TvResponse) {
                        bindDetails.imageUrl = URL_IMAGE + response.posterPath
                        bindDetails.overview = response.overview
                        bindDetails.popularity = response.popularity.toString()
                        bindDetails.releaseDate = response.first_air_date
                    }
                    override fun onError(e: Throwable) {
                        Log.d("error", "${e.stackTrace}")
                    }
                    override fun onComplete() {
                    }
                }
            )
    }
}

