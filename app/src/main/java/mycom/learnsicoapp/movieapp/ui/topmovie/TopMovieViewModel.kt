package mycom.learnsicoapp.movieapp.ui.topmovie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import mycom.learnsicoapp.movieapp.domain.Repository

/**
 * @author ll4
 * @date 12/6/2020
 */
class TopMovieViewModel @ViewModelInject constructor(
    private var repository: Repository
) : ViewModel() {

    fun getTopRated(pageId :Long) = repository.getTopRated(pageId)

}


