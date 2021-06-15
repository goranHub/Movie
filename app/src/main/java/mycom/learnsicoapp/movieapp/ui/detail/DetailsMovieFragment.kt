package mycom.learnsicoapp.movieapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import mycom.learnsicoapp.movieapp.data.database.entities.MovieEntity
import mycom.learnsicoapp.movieapp.data.database.entities.Rating
import mycom.learnsicoapp.movieapp.data.database.relations.MovieAndRating
import mycom.learnsicoapp.movieapp.data.database.relations.MovieRatingCrossRef
import mycom.learnsicoapp.movieapp.data.database.relations.UserMovieCrossRef
import mycom.learnsicoapp.movieapp.data.remote.firebase.FireStoreClass
import mycom.learnsicoapp.movieapp.databinding.FragmentMovieDetailsBinding
import mycom.learnsicoapp.movieapp.utils.MEDIATYP
import mycom.learnsicoapp.movieapp.utils.MOVIE_ID


@AndroidEntryPoint
class DetailsMovieFragment(val fireStoreClass: FireStoreClass) : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    var movieId = 0L
    var mediaTyp = ""

    val currentUser = fireStoreClass.currentUserID()

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getLong(MOVIE_ID, -1)?.let {movieId = it}
        arguments?.getString(MEDIATYP, "")?.let {mediaTyp = it}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMovieDetailsBinding.inflate(inflater)

        if ((mediaTyp == "movie") or (mediaTyp == "")) {
            updateUIMovie(movieId)
        } else {
            getTvShowById(movieId)
        }

        binding.data = viewModel.bindDetails
        insertSmiley()
        getSmileyByMovieId()
        deleteSmileyByMovieId()

        return binding.root
    }


    private fun insertSmiley() {
        binding.smiley.setSmileySelectedListener {
            lifecycleScope.launch {
                //for saved fragment by current user
                val crossRef = UserMovieCrossRef(currentUser, movieId.toString())
                viewModel.insertUserMovieCrossRef(crossRef)

                val movieRatingCrossRef = MovieRatingCrossRef(movieId.toString(), it.rating.toString())
                viewModel.insertMovieRatingCrossRef(movieRatingCrossRef)

                val ratingEntity = Rating(it.rating.toString(), movieId.toString())
                viewModel.insertRating(ratingEntity)

                val movieEntity = MovieEntity("")
                movieEntity.movieID =  movieId.toString()
                viewModel.insertMovie(movieEntity)
                //for saved all user fragment
            }
        }
    }

    private fun deleteSmileyByMovieId() {
        binding.btnDeleteAll.setOnClickListener {
            viewModel.deleteSmileyByMovieId(movieId.toString(), currentUser)
        }
    }


    private fun getSmileyByMovieId() {
        viewModel
            .getRating(movieId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<MovieAndRating>> {
                    override fun onSubscribe(d: Disposable) {
                    }
                    override fun onNext(response: List<MovieAndRating>) {
                        response.map {
                            setFaceBackgroundColor(it.rating.rating.toInt())
                        }
                    }
                    override fun onError(e: Throwable) {
                    }
                    override fun onComplete() {
                    }
                })
    }


    private fun setFaceBackgroundColor(rating: Int?) {
        if (rating == 1) {
            binding.smiley.setRating(rating, false)
        }
        if (rating == 2) {
            binding.smiley.setRating(rating, false)
        }
        if (rating == 3) {
            binding.smiley.setRating(rating, false)
        }
        if (rating == 4) {
            binding.smiley.setRating(rating, false)
        }
        if (rating == 5) {
            binding.smiley.setRating(rating, false)
        }
    }

    private fun getTvShowById(movieId: Long) {
        viewModel.getTvShowById(movieId)
    }

    private fun updateUIMovie(movieId: Long) {
        viewModel.getMovieByID(movieId)
    }
}


