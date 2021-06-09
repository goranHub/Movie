package mycom.learnsicoapp.movieapp.ui.profil

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage
import mycom.learnsicoapp.movieapp.utils.mapToUserModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mycom.learnsicoapp.movieapp.data.database.User
import mycom.learnsicoapp.movieapp.data.remote.firebase.model.UserFirebase
import mycom.learnsicoapp.movieapp.data.remote.response.user.UserModel
import mycom.learnsicoapp.movieapp.domain.Repository
import mycom.learnsicoapp.movieapp.utils.mapToUserEntity


/**
 * @author ll4
 * @date 1/22/2021
 */


class MyProfileViewModel @ViewModelInject constructor(
    @ApplicationContext val appContext: Context,
    private val repository: Repository
) : ViewModel() {

    var bindMyProfile = BindMyProfile()
    var statusProfileUpdateSuccess = MutableLiveData<Boolean?>()
    var userRemote = MutableLiveData<UserModel?>()
    var userFromDB = MutableLiveData<User?>()


    interface CallbackUpdateCollection {
        fun update(profileImageURL : String)
    }
    // called in FireStoreClass updateUserProfileData
    fun profileUpdateSuccess() {
        statusProfileUpdateSuccess.value = true
    }

    fun loadFromRemoteVM(userFirebase: UserFirebase) {

        userRemote.value = userFirebase.mapToUserModel()

        //insert into DB
        val user = userFirebase.mapToUserEntity()

        insertIntoDB(user)
    }
       private fun insertIntoDB(user:User){
        repository.insertUser(user)
    }

    fun getUserFromDbAndBind(currentUserID: String) {
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

                                userFromDB.value?.image = it.image
                                userFromDB.value?.name = it.name
                                userFromDB.value?.email = it.email

                                bindMyProfile.image = it.image.toString()
                                bindMyProfile.name = it.name.toString()
                                bindMyProfile.email = it.email.toString()
                            }
                        }
                    }
                    override fun onComplete() {
                    }
                }
            )
    }

    fun fileExtension(uri: Uri?): String? {
        val cr: ContentResolver = appContext.contentResolver
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(cr.getType(uri!!))
    }
}

