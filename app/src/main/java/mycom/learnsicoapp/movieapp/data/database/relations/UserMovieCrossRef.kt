package mycom.learnsicoapp.movieapp.data.database.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["userId", "movieID"])
data class UserMovieCrossRef(
    val userId: String,
    @ColumnInfo(name = "movieID", index = true)
    val movieID: String
)