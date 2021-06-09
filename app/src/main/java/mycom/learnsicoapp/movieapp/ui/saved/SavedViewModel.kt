package mycom.learnsicoapp.movieapp.ui.saved

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import mycom.learnsicoapp.movieapp.data.database.ItemIdWithRatingForCurrentUser
import mycom.learnsicoapp.movieapp.data.remote.firebase.FireStoreClass
import mycom.learnsicoapp.movieapp.data.remote.response.movie.Movie
import mycom.learnsicoapp.movieapp.domain.Repository

/**
 * @author ll4
 * @date 12/6/2020
 */
class SavedViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    val adapter = SavedAdapter()
    var allElement = mutableListOf<Movie>()

    init {
        viewModelScope.launch {
            val currentUserID = FireStoreClass().currentUserID()
            val listOfRatings = getMoviesWithRatingFromDb(currentUserID)
            apiCallForMoviesImage(listOfRatings)
        }
    }

    private suspend fun getMoviesWithRatingFromDb(currentUser: String): List<ItemIdWithRatingForCurrentUser> {
        return repository.getRatingsOfUser(currentUser)
    }

    private fun apiCallForMoviesImage(itemIdWithRatingForCurrentUser: List<ItemIdWithRatingForCurrentUser>) {

        itemIdWithRatingForCurrentUser.map {

            for (element in it.rating) {

                val singleMovie = repository.getMovieByID(element.itemId.toLong())

                singleMovie
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        object : Observer<Movie> {
                            override fun onSubscribe(d: Disposable) {
                            }

                            override fun onNext(response: Movie) {
                                allElement.add(response)
                                adapter.addMovieWithRating(allElement, it.rating)
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
    }
}


