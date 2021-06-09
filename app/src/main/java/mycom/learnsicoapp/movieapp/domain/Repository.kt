package mycom.learnsicoapp.movieapp.domain

import io.reactivex.Observable
import mycom.learnsicoapp.movieapp.data.database.*
import mycom.learnsicoapp.movieapp.data.remote.NetworkDataSource
import mycom.learnsicoapp.movieapp.data.remote.response.movie.Movie
import mycom.learnsicoapp.movieapp.data.remote.response.movie.MovieResponse
import mycom.learnsicoapp.movieapp.data.remote.response.multi.Multi
import mycom.learnsicoapp.movieapp.data.remote.response.tvShow.TvResponse

/**
 * @author ll4
 * @date 1/7/2021
 */

class Repository(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource
) {

    fun getTopRated(pageId: Long): Observable<MovieResponse> {
        return networkDataSource
            .getTopRated(pageId)
    }

    fun getCrewByMovieId(movieId: Long): Observable<Movie> {
        return networkDataSource
            .getCrewByMovieId(movieId)
    }

    fun getMovieByID(movieId: Long): Observable<Movie> {
        return networkDataSource
            .getMovieByID(movieId)
    }

    fun getPopular(pageId: Long): Observable<MovieResponse> {
        return networkDataSource
            .getPopular(pageId)
    }

    fun getMulti(query: String): Observable<Multi> {
        return networkDataSource
            .getMulti(query)
    }

    fun getTvShowById(movieId: Long): Observable<TvResponse> {
        return networkDataSource
            .getTvShowById(movieId)
    }

    fun insertUser(user: User) {
        databaseDataSource.insertUser(user)
    }

    fun getAuthUserDB(): Observable<List<User>> {
        return databaseDataSource.getAuthUserDB()
    }

    fun insertSmiley(itemId: Int, rating: Int) {
        databaseDataSource.insertSmiley(itemId, rating)
    }

    suspend fun getRatingsOfUser(currentUser : String):  List<ItemIdWithRatingForCurrentUser>  {
        return databaseDataSource.getRatingsOfUser(currentUser)
    }

    fun getSmileyByMovieId(itemId: Int): Observable<ItemIdWithRating> {
        return databaseDataSource.getSmileyByMovieId(itemId)
    }

    fun deleteSmileyByMovieId(itemId: Int) {
        databaseDataSource.deleteSmileyByMovieId(itemId)
    }

    suspend fun insertUserMovieRatingCrossRef(crossRef: UserRatingsCrossRef){
        databaseDataSource.insertStudentSubjectCrossRef(crossRef)
    }
}
