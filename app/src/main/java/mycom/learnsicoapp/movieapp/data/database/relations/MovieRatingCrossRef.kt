package mycom.learnsicoapp.movieapp.data.database.relations

import androidx.room.Entity


@Entity(primaryKeys = ["movieID", "ratingId"])
data class MovieRatingCrossRef(
    val movieID: String,
    val ratingId: String
)