package mycom.learnsicoapp.movieapp.data.remote.response.user

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kotlinx.android.parcel.Parcelize
import kotlin.properties.Delegates

/**
 * @author ll4
 * @date 1/24/2021
 */
@Parcelize
data class UserModel(

    val id: String = "",
    val name: String = "",
    val email: String = "",
    var image: String = "",
    val movieId: String = "",
    val movieRating: String = "",
    val fcmToken: String = ""

) : Parcelable ,BaseObservable(){
    @get:Bindable
    var imageProfile :String? by Delegates.observable("TEST image") { _, _, _ -> notifyPropertyChanged(BR.imageProfile) }
}