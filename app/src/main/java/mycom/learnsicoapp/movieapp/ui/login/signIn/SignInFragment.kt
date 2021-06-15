package mycom.learnsicoapp.movieapp.ui.login.signIn

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import mycom.learnsicoapp.movieapp.EntryActivity
import mycom.learnsicoapp.movieapp.R
import mycom.learnsicoapp.movieapp.data.remote.firebase.FireStoreClass
import mycom.learnsicoapp.movieapp.data.remote.firebase.model.UserFirebase
import mycom.learnsicoapp.movieapp.databinding.FragmentSignInBinding
import mycom.learnsicoapp.movieapp.di.Navigator
import mycom.learnsicoapp.movieapp.ui.BaseFragment
import mycom.learnsicoapp.movieapp.utils.PREF_NAME
import mycom.learnsicoapp.movieapp.utils.USERS
import mycom.learnsicoapp.movieapp.utils.setDrawerHeaderImage
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment(val fireStoreClass: FireStoreClass) : BaseFragment() {

    lateinit var binding: FragmentSignInBinding

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater)
        binding.btnSignIn.setOnClickListener { signInRegisteredUser() }
        return binding.root
    }

    private fun signInRegisteredUser() {
        val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
        val password: String = binding.etPassword.text.toString().trim { it <= ' ' }

        if (validateForm(email, password)) {
            showProgressDialog(resources.getString(R.string.please_wait))
            // Sign-In  FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        signInUser(this@SignInFragment)
                        (activity as EntryActivity).drawerLayout.open()
                    } else {
                        Toast.makeText(
                            requireContext(), task.exception!!.message, Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    private fun signInUser(fragment: SignInFragment) {
        fireStoreClass.fireBase.collection(USERS)
            .document(fireStoreClass.currentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.e(fragment.javaClass.simpleName, document.toString())
                val loggedInUser = document.toObject(UserFirebase::class.java)!!
                signInSuccess(loggedInUser)
            }
            .addOnFailureListener { e ->
                Log.e(fragment.javaClass.simpleName, "Error while getting loggedIn user details", e)
            }
    }

    fun signInSuccess(userFirebase: UserFirebase) {
        hideProgressDialog()
        setDrawerHeaderImage(userFirebase.image, (activity) as EntryActivity)

        val editor = sharedPreferences.edit()
        editor.putString(PREF_NAME, userFirebase.image)
        editor.apply()
        navigator.navigate(R.id.navDirectionTopMovieFragment)
    }
}