package mycom.learnsicoapp.movieapp.data.database

import androidx.room.*
import io.reactivex.Observable
import mycom.learnsicoapp.movieapp.data.database.entities.MovieEntity
import mycom.learnsicoapp.movieapp.data.database.entities.Rating
import mycom.learnsicoapp.movieapp.data.database.entities.User
import mycom.learnsicoapp.movieapp.data.database.relations.*

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM User")
    fun getAuthUserDB(): Observable<List<User>>

/*    @Query("SELECT * FROM MovieRatingEntity WHERE movieID =:movieID")
    fun getMovieAndRatingWithMovieID(movieID: String): Observable<MovieRatingEntity>

    @Query("DELETE FROM MovieRatingEntity  WHERE movieID =:movieID")
    suspend fun deleteSmileyByMovieId(movieID: String)*/

    @Query("DELETE FROM UserMovieCrossRef  WHERE movieID =:movieID AND userId =:userId")
    suspend fun deleteSmileyByMovieIdFormUserMovieCrossRef(movieID: String, userId: String)


    @Transaction
    @Query("SELECT * FROM MovieEntity")
    fun getRatingsOfMovie(): Observable<MovieWithRatings>

    @Transaction
    @Query("SELECT * FROM MovieEntity WHERE movieID = :movieID")
    fun getMovieAndRatingWithMovieID(movieID: String): Observable<MovieWithRatings>

    @Transaction
    @Query("SELECT * FROM MovieEntity WHERE movieID = :movieID")
    fun getRating(movieID: String): Observable<List<MovieAndRating>>


    @Query("DELETE FROM MovieEntity  WHERE movieID =:movieID")
    suspend fun deleteSmileyByMovieId(movieID: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRating(rating: Rating)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserMovieCrossRef(crossRef: UserMovieCrossRef)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieRatingCrossRef(crossRef: MovieRatingCrossRef)

    @Transaction
    @Query("SELECT * FROM User WHERE userId = :userId")
    fun getRatingsOfUser(userId: String): Observable<List<UsersWithMovies>>

}