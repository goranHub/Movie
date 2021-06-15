package mycom.learnsicoapp.movieapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Rating (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "rating")
    var rating: String,
    var movieID: String

)