package mycom.learnsicoapp.movieapp.ui.login.signIn

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kotlin.properties.Delegates

/**
 * @author ll4
 * @date 12/10/2020
 */
class BindSignIn() : BaseObservable() {

    @get:Bindable
    var emailSignIn :String? by Delegates.observable("") { _, _, _ -> notifyPropertyChanged(BR.emailSignIn) }

    @get:Bindable
    var passwordSignIn :String? by Delegates.observable("") { _, _, _ -> notifyPropertyChanged(BR.passwordSignIn) }

}