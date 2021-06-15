package mycom.learnsicoapp.movieapp.ui.profil

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mycom.learnsicoapp.movieapp.data.database.entities.User
import mycom.learnsicoapp.movieapp.data.remote.firebase.FireStoreClass
import mycom.learnsicoapp.movieapp.data.remote.firebase.model.UserFirebase
import mycom.learnsicoapp.movieapp.data.remote.response.user.UserModel
import mycom.learnsicoapp.movieapp.domain.Repository
import mycom.learnsicoapp.movieapp.utils.USERS
import mycom.learnsicoapp.movieapp.utils.mapToUserEntity


/**
 * @author ll4
 * @date 1/22/2021
 */


class MyProfileViewModel @ViewModelInject constructor(
    private val repository: Repository,
    private val fireStoreClass: FireStoreClass,
) : ViewModel() {

    var statusProfileUpdateSuccess = MutableLiveData<Boolean?>()
    var userFromFirebase = MutableLiveData<UserFirebase?>()
    var user = UserModel()

    fun loadUserFromFirebase() : MutableLiveData<UserFirebase?> {
        FireStoreClass().fireBase.collection(USERS)
            .document(fireStoreClass.currentUserID())
            .get()
            .addOnSuccessListener { document ->
                val loggedInUser = document.toObject(UserFirebase::class.java)!!
                userFromFirebase.value = loggedInUser
                user.imageProfile = userFromFirebase.value?.image
            }
            .addOnFailureListener {}
        return userFromFirebase
    }

    fun insertIntoDB(userFromFirebase : MutableLiveData<UserFirebase?> ){
        userFromFirebase.value?.let { repository.insertUser(it.mapToUserEntity()) }
    }

    fun getUserFromDbAndUploadUI(currentUserID: String) {
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
                            if (it.id == currentUserID) {
                                user.imageProfile = it.image.toString()
                            }
                        }
                    }
                    override fun onComplete() {
                    }
                }
            )
    }


}

