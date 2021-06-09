package mycom.learnsicoapp.movieapp.ui.login.signup

import android.text.TextUtils
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import mycom.learnsicoapp.movieapp.data.database.User
import mycom.learnsicoapp.movieapp.data.remote.firebase.model.UserFirebase
import mycom.learnsicoapp.movieapp.domain.Repository
import mycom.learnsicoapp.movieapp.utils.mapToUserEntity

/**
 * @author ll4
 * @date 1/26/2021
 */
class SignUpVM @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private fun insertTokenFromCurrentUserIntoDB(userDB: User) {
        repository
            .insertUser(userDB)
    }

    fun validateForm(name: String, email: String, password: String): String {
        return when {
            TextUtils.isEmpty(name) -> {

                "falseName"
            }
            TextUtils.isEmpty(email) -> {

                "falseEmail"
            }
            TextUtils.isEmpty(password) -> {
                "false"
            }
            else -> {
                "true"
            }
        }
    }

    fun insertInDB(userFirebase: UserFirebase) {
        insertTokenFromCurrentUserIntoDB(userFirebase.mapToUserEntity())
    }
}