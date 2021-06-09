package mycom.learnsicoapp.movieapp.ui.login.signIn

import android.text.TextUtils
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mycom.learnsicoapp.movieapp.data.database.User
import mycom.learnsicoapp.movieapp.data.remote.response.user.UserModel
import mycom.learnsicoapp.movieapp.domain.Repository



/**
 * @author ll4
 * @date 1/26/2021
 */
class SignInVM @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    var bindSignIn = BindSignIn()

    val formErrors = ObservableArrayList<FormErrors>()

    fun isFormValid(): Boolean {
        formErrors.clear()
        if (TextUtils.isEmpty(bindSignIn.emailSignIn)) {
            formErrors.add(FormErrors.INVALID_EMAIL)
        }
        if (TextUtils.isEmpty(bindSignIn.passwordSignIn)) {
            formErrors.add(FormErrors.INVALID_PASSWORD)
        }
        return formErrors.isEmpty()
    }
    
    val newUser = UserModel(bindSignIn.emailSignIn!!,bindSignIn.passwordSignIn!!)
    
    enum class FormErrors {
        INVALID_EMAIL,
        INVALID_PASSWORD
    }

    private fun getUser() {
        repository
            .getAuthUserDB()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<List<User>> {
                    override fun onSubscribe(d: Disposable) {
                    }
                    override fun onError(e: Throwable) {
                        Log.d("error", "${e.stackTrace}")
                    }
                    override fun onNext(response: List<User>) {
                        response.map {

                        }
                    }
                    override fun onComplete() {
                    }
                }
            )
    }
}