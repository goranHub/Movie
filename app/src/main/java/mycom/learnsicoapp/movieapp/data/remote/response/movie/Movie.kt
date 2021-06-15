package mycom.learnsicoapp.movieapp.data.remote.response.movie

/**
 * @author ll4
 * @date 12/6/2020
 */
data class Movie (
    val page: Int,
    val results: List<MovieResponse>
)