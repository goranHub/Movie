package mycom.learnsicoapp.movieapp.data.remote


import io.reactivex.Observable
import mycom.learnsicoapp.movieapp.data.remote.response.movie.Movie
import mycom.learnsicoapp.movieapp.data.remote.response.movie.MovieResponse
import mycom.learnsicoapp.movieapp.data.remote.response.multi.Multi
import mycom.learnsicoapp.movieapp.data.remote.response.tvShow.TvResponse
import mycom.learnsicoapp.movieapp.utils.API_KEY
import javax.inject.Inject

/**
 * @author ll4
 * @date 1/20/2021
 */

class NetworkDataSource @Inject constructor(
    private val movieService: RestApi
) {

    fun getTopRated(pageId: Long): Observable<MovieResponse> {
        return movieService.getTopRated(API_KEY, pageId.toString())
    }

    fun getPopular(pageId: Long): Observable<MovieResponse> {
        return movieService.getPopular(API_KEY, pageId.toString())
    }

    fun getMulti(query: String): Observable<Multi> {
        return movieService.getMulti(API_KEY, query)
    }

    fun getMovieByID(movieId: Long): Observable<Movie> {
        return movieService.getMovieByID(movieId, API_KEY)
    }

    fun getTvShowById(movieId: Long): Observable<TvResponse> {
        return movieService.getTvShowById(movieId, API_KEY)
    }

    fun getCrewByMovieId(movieId: Long): Observable<Movie> {
        return movieService.getCrewByMovieId(movieId, API_KEY)
    }
}