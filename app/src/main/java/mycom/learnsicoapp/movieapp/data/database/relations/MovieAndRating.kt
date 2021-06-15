package mycom.learnsicoapp.movieapp.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import mycom.learnsicoapp.movieapp.data.database.entities.MovieEntity
import mycom.learnsicoapp.movieapp.data.database.entities.Rating


data class MovieAndRating(

    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = "movieID",
        entityColumn = "rating",
    )
    val rating: Rating

)