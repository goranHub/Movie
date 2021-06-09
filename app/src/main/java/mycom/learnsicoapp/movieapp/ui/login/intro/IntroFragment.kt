package mycom.learnsicoapp.movieapp.ui.login.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mycom.learnsicoapp.movieapp.R
import mycom.learnsicoapp.movieapp.databinding.FragmentIntroBinding
import mycom.learnsicoapp.movieapp.di.Navigator
import javax.inject.Inject

@AndroidEntryPoint
class IntroFragment : Fragment() {

    lateinit var binding: FragmentIntroBinding

    @Inject
    lateinit var navigator: Navigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentIntroBinding.inflate(inflater)

        binding.btnSignIn.setOnClickListener {
            navigator.navigate(R.id.navDirectionSignInFragment)
        }

        binding.btnSignUp.setOnClickListener {
                navigator.navigate(R.id.navDirectionSignUpFragment)
        }

        return binding.root
    }
}