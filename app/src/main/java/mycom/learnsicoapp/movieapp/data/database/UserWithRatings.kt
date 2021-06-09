package mycom.learnsicoapp.movieapp.data.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


/**
 * @author ll4
 * @date 1/23/2021
 */


data class UserWithRatings(

    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "itemId",
        associateBy = Junction(UserRatingsCrossRef::class)
    )
    val rating: List<Rating>
)