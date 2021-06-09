package mycom.learnsicoapp.movieapp.data.database

import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author ll4
 * @date 1/20/2021
 */

class DatabaseDataSource @Inject constructor(
    private val dao: DatabaseDao
) {

    fun insertSmiley(itemId: Int, rating: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val rating = ItemIdWithRating(itemId.toString(), rating)
            dao.insertSmiley(rating)
        }
    }

    suspend fun getRatingsOfUser(currentUser : String): List<ItemIdWithRatingForCurrentUser> {
        return dao.getRatingsOfUser(currentUser)
    }

    fun insertUser(
        user: User
    )
    {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertUser(user)
        }
    }

    fun getAuthUserDB(): Observable<List<User>> {
        return dao.getAuthUserDB()
    }

    fun getSmileyByMovieId(itemId: Int): Observable<ItemIdWithRating> {
        return dao.getSmileyByMovieId(itemId)
    }


    fun deleteSmileyByMovieId(itemId: Int) {
       CoroutineScope(Dispatchers.IO).launch {
           dao.deleteSmileyByMovieId(itemId)
       }
    }

    suspend fun insertStudentSubjectCrossRef(crossRef : UserRatingsCrossRef){
        dao.insertStudentSubjectCrossRef(crossRef)
    }
}