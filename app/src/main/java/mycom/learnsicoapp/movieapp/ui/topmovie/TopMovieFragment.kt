package mycom.learnsicoapp.movieapp.ui.topmovie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mycom.learnsicoapp.movieapp.R
import mycom.learnsicoapp.movieapp.data.remote.response.movie.MovieResponse
import mycom.learnsicoapp.movieapp.databinding.FragmentMovieTopBinding
import mycom.learnsicoapp.movieapp.databinding.ItemMovieTopBinding
import mycom.learnsicoapp.movieapp.di.Navigator
import mycom.learnsicoapp.movieapp.ui.BaseFragment
import mycom.learnsicoapp.movieapp.ui.popular.BindMovie
import mycom.learnsicoapp.movieapp.utils.CREW_ID
import mycom.learnsicoapp.movieapp.utils.ITEM_ID
import javax.inject.Inject

@AndroidEntryPoint
class TopMovieFragment@Inject constructor(
    val adapter : TopMovieAdapter,
    var viewModel : TopMovieViewModel? = null
)  : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    private lateinit var binding: FragmentMovieTopBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = viewModel ?: ViewModelProvider(requireActivity()).get(TopMovieViewModel::class.java)
        binding = FragmentMovieTopBinding.inflate(inflater)
        binding.recylerViewFragmentTopMovie.adapter = adapter
        subscribeToObservers()

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@TopMovieFragment.viewModel
        }

        adapter.listenerCall = object : TopMovieAdapter.ListenerCall{
            override fun callback(binding: ItemMovieTopBinding) {
                binding.apply {
                    topMovieFragment = this@TopMovieFragment
                }
            }
        }

        scrollRecyclerView()

        return binding.root
    }


    fun openItem(movieId :Long){
        val bundleItemId = bundleOf(ITEM_ID to movieId)
        navigator.navigateWithBundle(R.id.navDirectionMoviesDetailsFragment, bundleItemId)
    }

    fun openCrew(crewId :Long){
        val bundleCrewId = bundleOf(CREW_ID to crewId)
        navigator.navigateWithBundle(R.id.navDirectionCrewMovieFragment, bundleCrewId)
    }

    private fun subscribeToObservers() {
        var pageId = 1L
        val popular = viewModel?.getTopRated(pageId)

        popular?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                object : Observer<MovieResponse> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(response: MovieResponse) {
                        val movieItemsList = response.results.map { BindMovie(it) }
                        adapter.addMovies(movieItemsList)
                        pageId++
                    }

                    override fun onError(e: Throwable) {
                        Log.d("error", "${e.stackTrace}")
                    }

                    override fun onComplete() {
                    }

                }
            )
    }

    private fun scrollRecyclerView() {
        binding.recylerViewFragmentTopMovie.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    subscribeToObservers()
                }
            }
        })
    }
}