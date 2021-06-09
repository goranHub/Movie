package mycom.learnsicoapp.movieapp.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


/**
 * @author ll4
 * @date 12/15/2020
 */

@Entity
@Parcelize
class Rating (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "itemId")
    var itemId: String,
    @ColumnInfo(name = "rating")
    var rating: Int
) : Parcelable