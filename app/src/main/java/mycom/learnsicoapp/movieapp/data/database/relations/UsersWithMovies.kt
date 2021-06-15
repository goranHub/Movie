package mycom.learnsicoapp.movieapp.data.database.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import mycom.learnsicoapp.movieapp.data.database.entities.MovieEntity
import mycom.learnsicoapp.movieapp.data.database.entities.User


data class UsersWithMovies(

    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "movieID",
        associateBy = Junction(UserMovieCrossRef::class)
    )
    val movieList: List<MovieEntity>

)