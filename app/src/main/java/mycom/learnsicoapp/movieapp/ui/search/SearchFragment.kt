package mycom.learnsicoapp.movieapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mycom.learnsicoapp.movieapp.R
import mycom.learnsicoapp.movieapp.databinding.FragmentMovieSearchBinding
import mycom.learnsicoapp.movieapp.databinding.ItemMovieSearchBinding
import mycom.learnsicoapp.movieapp.di.Navigator
import mycom.learnsicoapp.movieapp.utils.ITEM_ID
import mycom.learnsicoapp.movieapp.utils.MEDIATYP
import javax.inject.Inject


/**
 * @author ll4
 * @date 1/1/2021
 */

@AndroidEntryPoint
class SearchFragment @Inject constructor(
    val adapter: SearchAdapter,
    var viewModel: SearchViewModel? = null
) : Fragment(R.layout.fragment_movie_search), AdapterView.OnItemSelectedListener,
    AdapterView.OnItemClickListener {

    private lateinit var searchSuggestionsAdapter: SearchSuggestionsAdapter
    private lateinit var binding: FragmentMovieSearchBinding
    private var selectedMovie = ""
    private var movieList = arrayListOf<String>()

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            viewModel ?: ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)

        searchSuggestionsAdapter = SearchSuggestionsAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMovieSearchBinding.inflate(inflater)

        hideProgressBar()

        viewModel!!.adapter = adapter

        adapter.clearItems()

        binding.etTvShows.threshold = 2
        binding.etTvShows.setAdapter(searchSuggestionsAdapter)
        binding.etTvShows.onItemClickListener = this


        binding.etMovies.threshold = 2
        binding.etMovies.setAdapter(searchSuggestionsAdapter)
        binding.etMovies.onItemClickListener = this


        binding.etTvShows.doOnTextChanged { text, _, _, _ ->
            binding.progressBar.visibility = View.VISIBLE
            viewModel?.fetchMoviesAndTvShowsList(text.toString())
            observeData("tvshow")
        }


        binding.etMovies.doOnTextChanged { text, _, _, _ ->
            binding.progressBar.visibility = View.VISIBLE
            viewModel?.fetchMoviesAndTvShowsList(text.toString())
            observeData("movie")
        }

        binding.rvMovies.apply {
            adapter = this@SearchFragment.adapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        adapter.listenerCall = object : SearchAdapter.ListenerCall {
            override fun callback(binding: ItemMovieSearchBinding) {
                binding.apply {
                    searchFragment = this@SearchFragment
                }
            }
        }
        return binding.root
    }


    private fun observeData(type: String) {
        val response = viewModel?.apiData()

        response?.observe(viewLifecycleOwner, Observer { listMultiBind ->
            if (listMultiBind.isNotEmpty()) {
                movieList.clear()

                when (type) {
                    "movie" -> {
                        for ((index) in listMultiBind.withIndex()) {
                            if (listMultiBind[index].movie.originalTitle != null) {
                                movieList.add(listMultiBind[index].movie.originalTitle)
                            }
                        }
                    }
                        "tvshow" -> {
                            for ((index) in listMultiBind.withIndex()) {
                                if (listMultiBind[index].movie.name != null) {
                                    movieList.add(listMultiBind[index].movie.name)
                                }
                            }
                        }
                    }
                }

                searchSuggestionsAdapter.setData(movieList)
                searchSuggestionsAdapter.notifyDataSetChanged()
                hideProgressBar()
        })
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedMovie = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        SearchUtils.hideKeyboard(requireContext(), view)
        SearchUtils.showToastMessage(requireContext(), movieList[position])
        adapter.clearItems()
        viewModel?.fetchMoviesAndTvShowsList(movieList[position])
    }


    fun openDetails(movieId: Long, mediaTyp: String) {
        val bundlePostIdAndMediaTyp = bundleOf(ITEM_ID to movieId, MEDIATYP to mediaTyp)
        navigator.navigateWithBundle(R.id.navDirectionMoviesDetailsFragment, bundlePostIdAndMediaTyp)
    }


    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
        binding.progressBar2.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.progressBar2.visibility = View.VISIBLE
    }
}

