package mycom.learnsicoapp.movieapp.data.database.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import mycom.learnsicoapp.movieapp.data.database.entities.MovieEntity
import mycom.learnsicoapp.movieapp.data.database.entities.Rating


data class MovieAndRating(

    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = "movieID",
        entityColumn = "ratingId",
        associateBy = Junction(MovieRatingCrossRef::class)
    )
    val rating: Rating

)