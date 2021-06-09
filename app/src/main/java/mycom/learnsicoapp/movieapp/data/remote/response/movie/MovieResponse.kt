package mycom.learnsicoapp.movieapp.data.remote.response.movie

/**
 * @author ll4
 * @date 12/6/2020
 */
data class MovieResponse (
    val page: Int,
    val results: List<Movie>
)