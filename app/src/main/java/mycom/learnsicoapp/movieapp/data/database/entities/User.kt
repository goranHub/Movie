package mycom.learnsicoapp.movieapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "userId")
    var id: String,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "email")
    var email: String?,
    @ColumnInfo(name = "image")
    var image: String?,
    @ColumnInfo(name = "fcmToken")
    var fcmToken: String?
)

