package mycom.learnsicoapp.movieapp.domain

import io.reactivex.Observable
import mycom.learnsicoapp.movieapp.data.database.*
import mycom.learnsicoapp.movieapp.data.database.entities.MovieEntity
import mycom.learnsicoapp.movieapp.data.database.entities.Rating
import mycom.learnsicoapp.movieapp.data.database.entities.User
import mycom.learnsicoapp.movieapp.data.database.relations.*
import mycom.learnsicoapp.movieapp.data.remote.NetworkDataSource
import mycom.learnsicoapp.movieapp.data.remote.response.movie.MovieResponse
import mycom.learnsicoapp.movieapp.data.remote.response.movie.Movie
import mycom.learnsicoapp.movieapp.data.remote.response.multi.Multi
import mycom.learnsicoapp.movieapp.data.remote.response.tvShow.TvResponse


class Repository(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource
) {

    fun getTopRated(pageId: Long): Observable<Movie> {
        return networkDataSource.getTopRated(pageId)
    }

    fun getCrewByMovieId(movieId: Long): Observable<MovieResponse> {
        return networkDataSource.getCrewByMovieId(movieId)
    }

    fun getMovieByIDFromNetwork(movieId: Long): Observable<MovieResponse> {
        return networkDataSource.getMovieByID(movieId)
    }

    fun getPopular(pageId: Long): Observable<Movie> {
        return networkDataSource.getPopular(pageId)
    }

    fun getMulti(query: String): Observable<Multi> {
        return networkDataSource.getMulti(query)
    }

    fun getTvShowById(movieId: Long): Observable<TvResponse> {
        return networkDataSource.getTvShowById(movieId)
    }

    fun insertUser(user: User) {
        databaseDataSource.insertUser(user)
    }

    fun insertMovie(movie: MovieEntity) {
        databaseDataSource.insertMovie(movie)
    }

    fun insertRating(rating: Rating) {
        databaseDataSource.insertRating(rating)
    }

    fun getAuthUserDB(): Observable<List<User>> {
        return databaseDataSource.getAuthUserDB()
    }

    fun getRatingsOfUser(currentUser: String): Observable<List<UsersWithMovies>> {
        return databaseDataSource.getRatingsOfUser(currentUser)
    }

    fun getRatingsOfMovie(): Observable<List<MovieAndRatings>> {
        return databaseDataSource.getRatingsOfMovie()
    }
    fun getMovieAndRatingWithMovieID(movieID: String): Observable<MovieAndRatings> {
        return databaseDataSource.getMovieAndRatingWithMovieID(movieID)
    }

    fun getRating(movieID: String): Observable<MovieAndRating> {
        return databaseDataSource.getRating(movieID)
    }

    fun deleteSmileyByMovieId(movieID: String, userID: String) {
        databaseDataSource.deleteSmileyByMovieId(movieID, userID)
    }

    suspend fun insertUserMovieCrossRef(crossRef: UserMovieCrossRef) {
        databaseDataSource.insertUserMovieCrossRef(crossRef)
    }
    suspend fun insertMovieRatingCrossRef(crossRef: MovieRatingCrossRef) {
        databaseDataSource.insertMovieRatingCrossRef(crossRef)
    }
}
