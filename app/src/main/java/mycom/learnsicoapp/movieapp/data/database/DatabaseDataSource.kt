package mycom.learnsicoapp.movieapp.data.database

import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mycom.learnsicoapp.movieapp.data.database.entities.MovieEntity
import mycom.learnsicoapp.movieapp.data.database.entities.Rating
import mycom.learnsicoapp.movieapp.data.database.entities.User
import mycom.learnsicoapp.movieapp.data.database.relations.*
import javax.inject.Inject


class DatabaseDataSource @Inject constructor(
    private val dao: DatabaseDao
) {

    fun getRatingsOfUser(currentUser : String): Observable<List<UsersWithMovies>> {
        return dao.getRatingsOfUser(currentUser)
    }

    fun getRatingsOfMovie(): Observable<List<MovieAndRatings>> {
        return dao.getRatingsOfMovie()
    }

    fun insertUser(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertUser(user)
        }
    }

    fun insertMovie(movie: MovieEntity){
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertMovie(movie)
        }
    }

    fun insertRating(rating: Rating){
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertRating(rating)
        }
    }


    fun deleteSmileyByMovieId(movieID: String, userID: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteSmileyByMovieId(movieID)
            dao.deleteSmileyByMovieIdFormUserMovieCrossRef(movieID, userID)
        }
    }

    fun getAuthUserDB(): Observable<List<User>> {
        return dao.getAuthUserDB()
    }

    fun getMovieAndRatingWithMovieID(movieID: String): Observable<MovieAndRatings> {
        return dao.getMovieAndRatingWithMovieID(movieID)
    }

    fun getRating(movieID: String): Observable<MovieAndRating> {
        return dao.getRating(movieID)
    }


    suspend fun insertUserMovieCrossRef(crossRef : UserMovieCrossRef){
        dao.insertUserMovieCrossRef(crossRef)
    }

    suspend fun insertMovieRatingCrossRef(crossRef : MovieRatingCrossRef){
        dao.insertMovieRatingCrossRef(crossRef)
    }
}