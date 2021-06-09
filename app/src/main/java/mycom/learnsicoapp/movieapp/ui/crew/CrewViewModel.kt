package mycom.learnsicoapp.movieapp.ui.crew

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mycom.learnsicoapp.movieapp.data.remote.response.movie.Movie
import mycom.learnsicoapp.movieapp.domain.Repository

class CrewViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    val adapter = CrewAdapter()

    fun getCrewByMovieId(movieId: Long) {
        repository
            .getCrewByMovieId(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<Movie> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(response: Movie) {
                        val list = response.credits?.cast
                            ?.filter { !response.posterPath.isNullOrBlank() }
                            ?.distinctBy { it.profilePath }
                            ?.map { CrewObservable(it) }
                        adapter.addCast(list)
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
