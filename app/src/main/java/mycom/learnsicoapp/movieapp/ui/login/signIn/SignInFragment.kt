package mycom.learnsicoapp.movieapp.ui.login.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import mycom.learnsicoapp.movieapp.R
import mycom.learnsicoapp.movieapp.data.remote.firebase.FireStoreClass
import mycom.learnsicoapp.movieapp.data.remote.firebase.model.UserFirebase
import mycom.learnsicoapp.movieapp.databinding.FragmentSignInBinding
import mycom.learnsicoapp.movieapp.di.Navigator
import mycom.learnsicoapp.movieapp.ui.BaseFragment
import javax.inject.Inject

class SignInFragment : BaseFragment() {

    lateinit var binding: FragmentSignInBinding

    @Inject
    lateinit var navigator: Navigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSignInBinding.inflate(inflater)

        binding.btnSignIn.setOnClickListener {signInRegisteredUser()}

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
                        FireStoreClass().signInUser(this@SignInFragment)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            task.exception!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    fun signInSuccess(user: UserFirebase) {
        hideProgressDialog()
        navigator.navigate(R.id.navDirectionTopMovieFragment)
    }
}