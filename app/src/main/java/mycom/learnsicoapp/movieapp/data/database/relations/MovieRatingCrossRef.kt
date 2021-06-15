package mycom.learnsicoapp.movieapp.data.database.relations

import androidx.room.Entity

/**
 * @author lllhr
 * @date 6/15/2021
 */
@Entity(primaryKeys = ["movieID", "rating"])
data class MovieRatingCrossRef(
    val movieID: String,
    val rating: String
)