package mycom.learnsicoapp.movieapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "movieID")
    var movieID: String
)