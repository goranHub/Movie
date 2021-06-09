package mycom.learnsicoapp.movieapp.ui.detail

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kotlin.properties.Delegates

/**
 * @author ll4
 * @date 12/19/2020
 */
class BindDetails : BaseObservable() {

    @get:Bindable
    var imageUrl by Delegates.observable("TEST imageUrl") { _, _, _ -> notifyPropertyChanged(BR.imageUrl) }

    @get:Bindable
    var overview by Delegates.observable("TEST overview") { _, _, _ -> notifyPropertyChanged(BR.overview) }

    @get:Bindable
    var popularity by Delegates.observable("TEST popularity") { _, _, _ -> notifyPropertyChanged(BR.popularity) }

    @get:Bindable
    var releaseDate by Delegates.observable("TEST releaseDate") { _, _, _ -> notifyPropertyChanged(BR.releaseDate) }
}