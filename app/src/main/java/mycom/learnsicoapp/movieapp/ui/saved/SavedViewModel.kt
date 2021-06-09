package mycom.learnsicoapp.movieapp.ui.saved

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mycom.learnsicoapp.movieapp.data.database.UserWithRatings
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

    suspend fun getRatingsOfUser(curenntUSer :String): List<UserWithRatings> {
        return repository.getRatingsOfUser(curenntUSer)
    }
    
    fun getByMovieID(list: List<UserWithRatings>) {

        list.map {

            val listOfRatingsForCurrentUserFromDB = it.rating

            for (element in listOfRatingsForCurrentUserFromDB) {
                val singleMovie =
                    repository
                        .getMovieByID(element.itemId.toLong())

                singleMovie
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        object : Observer<Movie> {
                            override fun onSubscribe(d: Disposable) {
                            }

                            override fun onNext(response: Movie) {
                                allElement.add(response)
                                adapter.addMovieWithRating(allElement, listOfRatingsForCurrentUserFromDB)
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


