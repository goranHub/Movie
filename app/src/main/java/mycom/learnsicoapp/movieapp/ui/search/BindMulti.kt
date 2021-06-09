package mycom.learnsicoapp.movieapp.ui.search

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import mycom.learnsicoapp.movieapp.data.remote.response.multi.MultiResult
import mycom.learnsicoapp.movieapp.utils.URL_IMAGE
import kotlin.properties.Delegates

/**
 * @author ll4
 * @date 12/10/2020
 */
class BindMulti(val movie: MultiResult) : BaseObservable() {

    @get:Bindable
    var imageUrl by Delegates.observable("") { _, _, _ -> notifyPropertyChanged(BR.imageUrl) }

    init {
        imageUrl = URL_IMAGE + movie.poster_path
    }
}