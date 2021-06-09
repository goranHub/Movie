package mycom.learnsicoapp.movieapp.ui.search

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mycom.learnsicoapp.movieapp.data.remote.response.multi.Multi
import mycom.learnsicoapp.movieapp.domain.Repository

/**
 * @author ll4
 * @date 1/4/2021
 */
class SearchViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val apiDataForAdapter: MutableLiveData<List<BindMulti>> = MutableLiveData()

    lateinit var adapter: SearchAdapter


    fun fetchMoviesAndTvShowsList(query: String) {

        val searchForImage = repository.getMulti(query)

        searchForImage
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                object : Observer<Multi> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(response: Multi) {
                        val movieResponse =
                            response
                                .results
                                .filter { !it.poster_path.isNullOrBlank() }
                                .distinctBy { it.poster_path }
                                .map { BindMulti(it) }


                        apiDataForAdapter.postValue(movieResponse)
                        adapter.updateItems(movieResponse)
                    }

                    override fun onError(e: Throwable) {
                        Log.d("error", "${e.stackTrace}")
                    }

                    override fun onComplete() {
                    }
                }
            )
    }

    fun apiData(): MutableLiveData<List<BindMulti>> {
        return apiDataForAdapter
    }

}
