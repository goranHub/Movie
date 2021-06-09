package mycom.learnsicoapp.movieapp.ui.crew

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import mycom.learnsicoapp.movieapp.data.remote.response.movie.Cast
import mycom.learnsicoapp.movieapp.utils.URL_IMAGE
import kotlin.properties.Delegates

/**
 * @author ll4
 * @date 12/10/2020
 */


class CrewObservable(cast: Cast) : BaseObservable() {

    @get:Bindable
    var imageActorUrl by Delegates.observable("") { _, _, _ -> notifyPropertyChanged(BR.imageUrl) }

    @get:Bindable
    var actorName by Delegates.observable("") { _, _, _ -> notifyPropertyChanged(BR.actorName) }

    @get:Bindable
    var character by Delegates.observable("") { _, _, _ -> notifyPropertyChanged(BR.character) }

    init {
        imageActorUrl = URL_IMAGE + cast.profilePath
        actorName = cast.name
        character = cast.character
    }
}