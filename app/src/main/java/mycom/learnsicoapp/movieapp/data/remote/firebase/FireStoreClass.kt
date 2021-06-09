package mycom.learnsicoapp.movieapp.data.remote.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import mycom.learnsicoapp.movieapp.data.remote.firebase.model.UserFirebase
import mycom.learnsicoapp.movieapp.ui.login.signIn.SignInFragment
import mycom.learnsicoapp.movieapp.ui.login.signup.SignUpFragment
import mycom.learnsicoapp.movieapp.ui.profil.MyProfileViewModel
import mycom.learnsicoapp.movieapp.utils.USERS
import javax.inject.Inject

class FireStoreClass @Inject constructor() {

    val fireBase = FirebaseFirestore.getInstance()

    fun currentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }


}