package mycom.learnsicoapp.movieapp.ui.login.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_progress.*
import mycom.learnsicoapp.movieapp.R
import mycom.learnsicoapp.movieapp.data.remote.firebase.FireStoreClass
import mycom.learnsicoapp.movieapp.data.remote.firebase.model.UserFirebase
import mycom.learnsicoapp.movieapp.databinding.FragmentSignUpBinding
import mycom.learnsicoapp.movieapp.di.Navigator
import mycom.learnsicoapp.movieapp.ui.BaseFragment
import javax.inject.Inject

/**
 * @author ll4
 * @date 1/14/2021
 */
@AndroidEntryPoint
class SignUpFragment : BaseFragment() {

    lateinit var binding: FragmentSignUpBinding
    lateinit var userFirebase: UserFirebase
    private val viewModel by viewModels<SignUpVM>()

    @Inject
    lateinit var navigator: Navigator


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSignUpBinding.inflate(inflater)

        binding.btnSignUp.setOnClickListener {
            registerUser()
        }

        return binding.root
    }

    private fun registerUser() {

        val name: String = binding.etName.text.toString().trim { it <= ' ' }
        val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
        val password: String = binding.etPassword.text.toString().trim { it <= ' ' }

        if (viewModel.validateForm(name, email, password) == "falseName") {
            showErrorSnackBar("Please enter name.")
        }

        if (viewModel.validateForm(name, email, password) == "falseEmail") {
            showErrorSnackBar("Please enter email.")
        }

        if (viewModel.validateForm(name, email, password) == "false") {
            showErrorSnackBar("Please enter password.")
        }

        if (viewModel.validateForm(name, email, password) == "true") {
            showProgressDialog(resources.getString(R.string.please_wait))

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->
                        if (task.isSuccessful) {

                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            val registeredEmail = firebaseUser.email!!

                            userFirebase =
                                UserFirebase(
                                    firebaseUser.uid, name, registeredEmail
                                )

                            FireStoreClass().registerUser(this@SignUpFragment, userFirebase)

                            viewModel.insertInDB(userFirebase)

                        } else {
                            Toast.makeText(
                                requireContext(),
                                task.exception!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
        }
    }


    fun userRegisteredSuccess() {
        Toast.makeText(requireContext(), "You have successfully registered.", Toast.LENGTH_SHORT)
            .show()
        hideProgressDialog()
        navigator.navigate(R.id.navDirectionTopMovieFragment)
    }

    override fun hideProgressDialog() {
        dialog.dismiss()
    }
}