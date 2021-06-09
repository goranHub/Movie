package mycom.learnsicoapp.movieapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * @author ll4
 * @date 1/24/2021
 */
@Entity(primaryKeys = ["id", "itemId"])
data class UserRatingsCrossRef(
    val id: String,
    @ColumnInfo(name = "itemId", index = true)
    val itemId: String
)